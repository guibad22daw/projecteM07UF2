window.onload = function () {
    var weather;
    var url = "https://api.open-meteo.com/v1/forecast?latitude=41.39&longitude=2.16&daily=weathercode,temperature_2m_max&current_weather=true&timezone=auto";
    var request = new XMLHttpRequest();
    request.open('GET', url);
    request.responseType = 'json';
    request.send();
    request.onload = function () {
        weather = request.response;
        console.log(weather);
        document.getElementById("weather").innerHTML = weather.current_weather.weathercode;

        weather.current_weather.is_day ? (
            document.getElementById("weather").innerHTML = `<img src="assets/img/weather-icons/weathercode-${weather.current_weather.weathercode}.svg" alt="weather">`
            ) : (console.log('second'));
        document.getElementById("temperature").innerHTML = weather.current_weather.temperature;
    }
}