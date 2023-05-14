import navbarHandler from "./navbar.js";

window.onload = function () {
    navbarHandler();
    document.getElementById("card-content").style.opacity = "0";

    if (location === "/home") {
        document.getElementById("favorits-icon").classList.remove("actiu");
        document.getElementById("home-icon").classList.add("actiu");
        document.getElementById("favorits-icon-container").style.cssText = "";
        document.getElementById("home-icon-container").style.cssText = "background-color: rgb(231, 231, 231)";
    } else if (location === "/favorits") {
        document.getElementById("home-icon").classList.remove("actiu");
        document.getElementById("favorits-icon").classList.add("actiu");
        document.getElementById("home-icon-container").style.cssText = "";
        document.getElementById("favorits-icon-container").style.cssText = "background-color: rgb(231, 231, 231)";
    }

    const cards = document.querySelectorAll('.card');

    for (let i = 0; i < cards.length; i++) {
        const card = cards[i];
        const ciutat = card.querySelector('#nom-ciutat').getAttribute('data');
        const lat = card.querySelector('#lat').textContent;
        const lon = card.querySelector('#lon').textContent;
        console.log('ciutat,lat,lon', ciutat, lat, lon);
        getWeather(lat, lon, card, ciutat);
    }

    async function getWeather(lat, lon, card, ciutat) {
        const response = await fetch(`https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&hourly=temperature_2m,relativehumidity_2m,precipitation_probability,weathercode,is_day&daily=weathercode,temperature_2m_max,temperature_2m_min,precipitation_probability_max&current_weather=true&timezone=auto`);
        const data = await response.json();

        nouPanellTemps(data, card, ciutat);
    }

    function nouPanellTemps(data, card, cadenaCiutat) {
        const dataActual = data.current_weather.time;
        const indexOfHoraActual = data.hourly.time.indexOf(dataActual);
        const previsionsHores = data.hourly.time.slice(indexOfHoraActual + 1, indexOfHoraActual + 11);
        const previsionsDies = data.daily.time;

        let cadenaCerca = cadenaCiutat.split(", ");
        let ciutat = cadenaCerca[0];
        let provinciaOPais = cadenaCerca[1];
        let pais = cadenaCerca[2];
        card.querySelector("#nom-ciutat").innerHTML = ciutat;
        card.querySelector("#nom-provincia-pais").innerHTML = `${pais ? provinciaOPais+", "+pais : provinciaOPais}`;
        data.current_weather.is_day ? (
            card.querySelector("#weather").innerHTML = `<img src="assets/img/weather-icons/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`
        ) : (card.querySelector("#weather").innerHTML = `<img src="assets/img/weather-icons/night/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`);
        card.querySelector("#temperatura-i-humitat").innerHTML = `<h5>${data.current_weather.temperature} ºC </h5><h5>${data.hourly.relativehumidity_2m[indexOfHoraActual]} %</h5>`;

        card.querySelector("#titol-previsionsHores").innerHTML = `Properes 10 hores`;
        card.querySelector("#previsionsHores").innerHTML = "";
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
                                    <div class="precipitacioHora">${data.hourly.precipitation_probability[indexOfHoraActual + index]} % <img src="assets/img/weather-icons/weathercode-55.svg" alt="precipitation_probability" style="width: 20px;"/></div>`;
            card.querySelector("#previsionsHores").appendChild(horaActual);
        });

        const diesSetmana = ["Diumenge", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte"];
        card.querySelector("#previsionsDies").innerHTML = "<h6>Propers 7 dies </h6>";
        previsionsDies.map((dia, index) => {
            const dataAAA_MM_DD = new Date(dia.slice(0, 10));
            const diaSetmana = diesSetmana[dataAAA_MM_DD.getDay()];

            const diaActual = document.createElement("div");
            diaActual.className = "previsioDia";
            diaActual.setAttribute("key", `${index}`);

            diaActual.innerHTML = `<div class="dia">
                                        <p>${index == 0 ? "Avui" : diaSetmana}</p>
                                        <div class="icona-precipitacions">
                                            <div class="iconaDia"><img src="assets/img/weather-icons/weathercode-${data.daily.weathercode[index]}.svg" alt="weather" style="width: 40px;"></div>
                                            <div class="precipitacions">${data.daily.precipitation_probability_max[index]} % <img src="assets/img/weather-icons/weathercode-55.svg" alt="precipitation_probability" style="width: 25px;"/></div>
                                        </div>
                                    </div>
                                    <div class="infoDia">
                                        <div class="climaDia">
                                            <div class="temperaturaDia"><p class="max-temp">${Math.round(data.daily.temperature_2m_max[index])} ºC</p> <p class="min-temp">${Math.round(data.daily.temperature_2m_min[index])} ºC</p></div>
                                        </div>
                                    </div>`;
            card.querySelector("#previsionsDies").appendChild(diaActual);
        });
        document.getElementById("card-content").style.opacity = "1";
        document.getElementById("card-content").style.transitionDuration = "1s";
    }

    const removeFavoriteBtns = document.querySelectorAll('#boto');

    removeFavoriteBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            btn.querySelector("#afegir").classList.add("noActiu");
            const ciutatCard = JSON.parse(btn.getAttribute('data'));

            const esborraCiutat = {
                nom: ciutatCard.nom,
                latitud: ciutatCard.latitud,
                longitud: ciutatCard.longitud
            };

            fetch('/esborraCiutat', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(esborraCiutat),
            }).then(response => {
                if (response.ok) {
                    console.log('Petició efectuada correctament.');
                    window.location.reload();
                } else {
                    console.log("Error al efectuar la petició.");
                }
            });
        });
    });
} 