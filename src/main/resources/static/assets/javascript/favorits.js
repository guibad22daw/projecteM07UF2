window.onload = function () {
    const suggestions = document.getElementById("suggestions");
    const cerca = document.getElementById('cerca');

    if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(async (position) => {
            const user_lat = position.coords.latitude;
            const user_lon = position.coords.longitude;
            const response = await fetch(`https://nominatim.openstreetmap.org/reverse?lat=41.39&lon=2.17&format=json`);
            const data = await response.json();
            document.getElementById('nom-ciutat').innerHTML = `${data.address.city}`;

            getWeather(user_lat, user_lon);
        });
    } else {
        console.log('La geolocalització no està disponible.');
    }

    document.onmousedown = function () {
        const isClickInsideSuggestions = suggestions.contains(event.target);
        const isClickInsideCerca = cerca.contains(event.target);

        if (!isClickInsideSuggestions && !isClickInsideCerca) {
            suggestions.style.display = 'none';
            cerca.style.borderRadius = "20px";
        }
    };

    cerca.onclick = async function () {
        suggestions.style.display = "block";
        cerca.style.borderBottomLeftRadius = "10px";
        cerca.style.borderBottomRightRadius = "10px";
    }

    // Funció per suggerir ciutats
    cerca.onkeyup = async function () {
        const value = this.value;
        if (!value) {
            while (suggestions.firstChild) {
                suggestions.removeChild(suggestions.firstChild);
            }
            return;
        }
        getCiutat(value);
    };

    async function getCiutat(cadena) {
        try {
            const response = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${cadena}`);
            if (response.ok) {
                const data = await response.json();
                const resultats = data.results;

                while (suggestions.firstChild) {
                    suggestions.removeChild(suggestions.firstChild);
                }

                resultats.forEach(suggestion => {
                    const localitzacio = document.createElement("li");
                    localitzacio.value = `${suggestion.name}`;
                    localitzacio.innerHTML = `${suggestion.name}, ${suggestion.admin1}, ${suggestion.country}`;
                    localitzacio.onclick = () => {
                        suggestions.style.display = 'none';
                        cerca.style.borderRadius = "20px";
                        cerca.value = `${suggestion.name}, ${suggestion.admin1}, ${suggestion.country}`;
                        getWeather(suggestion.latitude, suggestion.longitude);
                    };
                    suggestions.appendChild(localitzacio);
                });
            }
        } catch (err) {
            console.log('err :>> ', err);
        }
    }

    document.getElementById('formCerca').onsubmit = async function (e) {
        e.preventDefault();
        ciutat = cerca.value;
        const response = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${ciutat}`);
        const data = await response.json();
        lat = data.results[0].latitude;
        lon = data.results[0].longitude;

        getWeather(lat, lon);
    };

    async function getWeather(lat, lon) {
        const response = await fetch(`https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&hourly=temperature_2m,relativehumidity_2m,precipitation_probability,weathercode,is_day&daily=weathercode,temperature_2m_max,temperature_2m_min,precipitation_probability_max&current_weather=true&timezone=auto`);
        const data = await response.json();
        const ciutatArray = cerca.value.split(',', 1)
        const novaCiutat = {
            nom: ciutatArray[0],
            latitud: lat,
            longitud: lon
        };
        localStorage.setItem('ciutat', JSON.stringify(novaCiutat));
        nouPanellTemps(data);
    }

    function nouPanellTemps(data) {
        document.getElementById("afegir").classList.remove("actiu");
        comprovaCiutatFavorita();
        const dataActual = data.current_weather.time;
        const indexOfHoraActual = data.hourly.time.indexOf(dataActual);
        const previsionsHores = data.hourly.time.slice(indexOfHoraActual + 1, indexOfHoraActual + 11);
        const previsionsDies = data.daily.time;

        if (cerca.value.length != 0) {
            document.getElementById('nom-ciutat').innerHTML = cerca.value.split(',', 1);
        }
        data.current_weather.is_day ? (
            document.getElementById("weather").innerHTML = `<img src="../static/assets/img/weather-icons/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`
        ) : (document.getElementById("weather").innerHTML = `<img src="../static/assets/img/weather-icons/night/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`);
        document.getElementById("temperatura-i-humitat").innerHTML = `<h5>${data.current_weather.temperature} ºC </h5><h5>${data.hourly.relativehumidity_2m[indexOfHoraActual]} %</h5>`;

        document.getElementById("titol-previsionsHores").innerHTML = `Properes 10 hores`;
        document.getElementById("previsionsHores").innerHTML = "";
        previsionsHores.map((hora, index) => {
            const horaActual = document.createElement("div");
            horaActual.className = "previsioHora";
            horaActual.setAttribute("key", `${index}`);
            horaActual.innerHTML = ` <div class="hora">${hora.slice(-5)}</div>`;

            if (data.hourly.is_day[indexOfHoraActual + index] === 0) {
                horaActual.innerHTML += `<div class="iconaHora"><img src="../static/assets/img/weather-icons/night/weathercode-${data.hourly.weathercode[indexOfHoraActual + index]}.svg" alt="weather" style="width: 40px;"></div>`;
            } else {
                horaActual.innerHTML += `<div class="iconaHora"><img src="../static/assets/img/weather-icons/weathercode-${data.hourly.weathercode[indexOfHoraActual + index]}.svg" alt="weather" style="width: 40px;"></div>`;
            }
            horaActual.innerHTML += `<div class="temperaturaHora">${data.hourly.temperature_2m[indexOfHoraActual + index]} ºC</div>
                                    <div class="humitatHora">${data.hourly.precipitation_probability[indexOfHoraActual + index]} % <img src="../static/assets/img/water-rain-drop-png.png" alt="precipitation_probability" style="width: 25px;"/></div>`;
            document.getElementById("previsionsHores").appendChild(horaActual);
        });

        const diesSetmana = ["Diumenge", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte"];
        document.getElementById("previsionsDies").innerHTML = "<h6>Propers 7 dies </h6>";
        previsionsDies.map((dia, index) => {
            const dataAAA_MM_DD = new Date(dia.slice(0, 10));
            const diaSetmana = diesSetmana[dataAAA_MM_DD.getDay()];

            const diaActual = document.createElement("div");
            diaActual.className = "previsioDia";
            diaActual.setAttribute("key", `${index}`);

            diaActual.innerHTML = `<div class="dia">
                                        <p>${index == 0 ? "Avui" : diaSetmana}</p>
                                        <div class="icona-precipitacions">
                                            <div class="iconaDia"><img src="../static/assets/img/weather-icons/weathercode-${data.daily.weathercode[index]}.svg" alt="weather" style="width: 40px;"></div>
                                            <div class="precipitacions">${data.daily.precipitation_probability_max[index]} % <img src="../static/assets/img/water-rain-drop-png.png" alt="precipitation_probability" style="width: 30px;"/></div>
                                        </div>
                                    </div>
                                    <div class="infoDia">
                                        <div class="climaDia">
                                            <div class="temperaturaDia"><p class="max-temp">${Math.round(data.daily.temperature_2m_max[index])} ºC</p> <p class="min-temp">${Math.round(data.daily.temperature_2m_min[index])} ºC</p></div>
                                        </div>
                                    </div>`;
            document.getElementById("previsionsDies").appendChild(diaActual);
        });
    }

    document.getElementById("boto").addEventListener("click", async () => {
        let url = "";

        if (document.getElementById("afegir").classList.contains("actiu")) {
            document.getElementById("afegir").classList.remove("actiu");
            url = "/esborraCiutat";
        } else {
            document.getElementById("afegir").classList.add("actiu");
            url = "/desaCiutat";
        }

        const novaCiutat = localStorage.getItem('ciutat');
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: novaCiutat,
        }).then(response => {
            if (response.ok) {
                console.log('Petició efectuada correctament.');
            } else {
                console.log("Error al efectuar la petició.");
            }
        });
    });

    async function comprovaCiutatFavorita() {
        const response = await fetch('/getCiutats', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
        const ciutats = await response.json();

        ciutats.forEach(ciutat => {
            const ciutatNom = ciutat.nom;
            if(cerca.value.includes(ciutatNom)) {
                console.log('cerca.value', cerca.value);
                document.getElementById("afegir").classList.add("actiu");
            }
        });
    }
} 