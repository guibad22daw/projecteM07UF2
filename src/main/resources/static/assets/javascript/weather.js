window.onload = function () {
    const suggestions = document.getElementById("suggestions");
    const cerca = document.getElementById('cerca');

    document.onmousedown = function () {
        const isClickInsideSuggestions = suggestions.contains(event.target);
        const isClickInsideCerca= cerca.contains(event.target);
      
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
                    console.log('suggestion', suggestion);
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

    document.getElementById('formCerca').onsubmit = async function(e){
        e.preventDefault();
        let ciutat = cerca.value;
        const response = await fetch(`https://geocoding-api.open-meteo.com/v1/search?name=${ciutat}`);
        const data = await response.json();
        console.log(data);
        getWeather(data.results[0].latitude, data.results[0].longitude);
    };

    async function getWeather(lat, lon) {
        const response = await fetch(`https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&daily=weathercode,temperature_2m_max&current_weather=true&timezone=auto`);
        const data = await response.json();
        console.log(data);
        document.getElementById("weather").innerHTML = data.current_weather.weathercode;
        data.current_weather.is_day ? (
            document.getElementById("weather").innerHTML = `<img src="assets/img/weather-icons/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`
            ) : (document.getElementById("weather").innerHTML = `<img src="assets/img/weather-icons/night/weathercode-${data.current_weather.weathercode}.svg" alt="weather">`);
        document.getElementById("temperature").innerHTML = data.current_weather.temperature;
    }
}