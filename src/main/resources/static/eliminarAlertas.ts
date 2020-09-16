
function eliminarAlertas() {
    const success = document.getElementById("success") as HTMLDivElement;
    const danger = document.getElementById("danger") as HTMLDivElement;
    const info = document.getElementById("info") as HTMLDivElement;
    
    eliminarAlerta(success);
    eliminarAlerta(danger);
    eliminarAlerta(info);
}

function eliminarAlerta(alert: HTMLDivElement) {
    if (alert != null) {
        alert.classList.add("animate__animated");
		setTimeout(() => {
            alert.classList.add("animate__backOutRight");
            setTimeout(() => {
                alert.remove();
            }, 1000);
        }, 4000);
    }
}

eliminarAlertas();