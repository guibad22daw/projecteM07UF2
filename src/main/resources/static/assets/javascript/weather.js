window.onload = function () {
    const suggestions = document.getElementById("suggestions");
    const cerca = document.getElementById('cerca');

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

        try {
            const response = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${value}`);

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
    };

    document.getElementById('formCerca').onsubmit = async function (e) {
        e.preventDefault();
        let ciutat = cerca.value;
        const response = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${ciutat}`);
        const data = await response.json();
        getWeather(data.results[0].latitude, data.results[0].longitude);
    };

    async function getWeather(lat, lon) {
        const response = await fetch(`https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&hourly=temperature_2m,relativehumidity_2m,precipitation_probability,weathercode,is_day&daily=weathercode,temperature_2m_max,temperature_2m_min,precipitation_probability_max&current_weather=true&timezone=auto`);
        const data = await response.json();
        document.getElementById("nom-ciutat").innerHTML = data.current_weather.weathercode;
        document.getElementById("weather").innerHTML = data.current_weather.weathercode;
        nouPanellTemps(data);
    }

    function nouPanellTemps(data) {
        console.log(data);
        const dataActual = data.current_weather.time;
        const indexOfHoraActual = data.hourly.time.indexOf(dataActual);
        const previsionsHores = data.hourly.time.slice(indexOfHoraActual + 1, indexOfHoraActual + 11);

        console.log('previsionsHores', previsionsHores);
        document.getElementById('nom-ciutat').innerHTML = cerca.value.split(',', 1);
        data.current_weather.is_day ? (
            document.getElementById("weather").innerHTML = `<img src="assets/img/weather-icons/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`
        ) : (document.getElementById("weather").innerHTML = `<img src="assets/img/weather-icons/night/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`);
        document.getElementById("temperatura").innerHTML = `${data.current_weather.temperature} ºC `;
        document.getElementById("humitat").innerHTML = `${data.hourly.relativehumidity_2m[indexOfHoraActual]} %`;
        previsionsHores.map((hora, index) => {
            const horaActual = document.createElement("div");
            horaActual.className = "previsioHora";
            horaActual.setAttribute("key", `${index}`);
            horaActual.innerHTML = ` <div class="hora">${hora.slice(-5)}</div>`;

            if (data.hourly.is_day[indexOfHoraActual + index] === 0) {
                horaActual.innerHTML += `<div class="iconaHora"><img src="assets/img/weather-icons/night/weathercode-${data.hourly.weathercode[indexOfHoraActual + index]}.svg" alt="weather" style="width: 40px;"></div>`;
            } else {
                horaActual.innerHTML += `<div class="iconaHora"><img src="assets/img/weather-icons/weathercode-${data.hourly.weathercode[indexOfHoraActual + index]}.svg" alt="weather" style="width: 40px;"></div>`;
            }
            horaActual.innerHTML += `<div class="temperaturaHora">${data.hourly.temperature_2m[indexOfHoraActual + index]} ºC</div>
                                    <div class="humitatHora">${data.hourly.relativehumidity_2m[indexOfHoraActual + index]} %</div></div>`;
            document.getElementById("previsionsHores").appendChild(horaActual);
        });

        //data.daily.time.forEach();
        document.getElementById('previsions')
    }
}