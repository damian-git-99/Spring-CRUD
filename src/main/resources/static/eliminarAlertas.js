function eliminarAlertas() {
    const success = document.getElementById("success");
    const danger = document.getElementById("danger");
    const info = document.getElementById("info");
    eliminarAlerta(success);
    eliminarAlerta(danger);
    eliminarAlerta(info);
}

function eliminarAlerta(alert) {
    if (alert != null) {
        alert.classList.add("animate__animated");
        setTimeout(function() {
            alert.classList.add("animate__backOutRight");
            setTimeout(function() {
                alert.remove();
            }, 1000);
        }, 4000);
    }
}
eliminarAlertas();