export default function navbarHandler() {
    const location = window.location.pathname;

    document.getElementById("favorits-icon").addEventListener("click", () => {
        document.getElementById("favorits-icon").classList.add("actiu");
    });

    document.getElementById("home-icon").addEventListener("click", () => {
        document.getElementById("home-icon").classList.add("actiu");
    });

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
}