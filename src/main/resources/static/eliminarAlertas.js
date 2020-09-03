function eliminarAlertas() {
    const success = document.getElementById("success");
    const danger = document.getElementById("danger");
    eliminarAlerta(success);
    eliminarAlerta(danger);
}

function eliminarAlerta(alert) {
    if (alert != null) {
        alert.classList.add("animate__animated");

        setTimeout(() => {
            alert.classList.add("animate__backOutRight");
            setTimeout(() => {
                alert.remove();
            }, 1000);
        }, 3000);
    }
}

eliminarAlertas();