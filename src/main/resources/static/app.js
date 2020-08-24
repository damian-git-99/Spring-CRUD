// Validacion del formulario desde el cliente
const form = document.getElementById("form");
form.addEventListener("submit", (evt) => {

    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;

    if (nombre == "") {
        console.log(document.getElementById("createAt").value);
        document.getElementById("nombre").classList.add("is-invalid");
        evt.preventDefault();
    } else {
        document.getElementById("nombre").classList.remove("is-invalid");
        document.getElementById("nombre").classList.add("is-valid");
    }



})