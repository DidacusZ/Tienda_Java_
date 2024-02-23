function revisarContraseña() {
    const clave = document.getElementById('clave').value;
    const clave2 = document.getElementById('clave2').value;
    const mensajeClave = document.getElementById('mensajeClave');

    if (clave === "" && clave2 === "") {
        mensajeClave.innerHTML = '<span class="badge bg-danger">No puede dejar los campos contraseñas vacíos</span>';
        mensajeClave.style.color = 'black';
        document.getElementById("boton").disabled = true;
        boton.style.backgroundColor = "#959595";
        
    } else if (clave === clave2) 
    {
            mensajeClave.innerHTML = '<span class="badge bg-success">Contraseña válida</span>';
            mensajeClave.style.color = 'black';
            document.getElementById("boton").disabled = false;
            boton.style.backgroundColor = "#5993d3";
    } else 
    {
        mensajeClave.innerHTML = '<span class="badge bg-danger">Las contraseñas introducidas no son iguales</span>';
        mensajeClave.style.color = 'black';
        document.getElementById("boton").disabled = true;
        boton.style.backgroundColor = "#959595";
    }
}


function mostrarNotificacion(titulo, mensaje, tipo) {
    Swal.fire({
        title: titulo,
        text: mensaje,
        icon: tipo,
        confirmButtonText: 'OK',
        customClass: {
            confirmButton: 'btn btn-primary'  
        }
    });
}

function confirmarLogout() {
    Swal.fire({
        title: '¿Estás seguro de que deseas cerrar sesión?',
        text: 'Serás redirigido a la página de bienvenida.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, cerrar sesión'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById('logoutForm').submit();
        } else {
            console.log('Logout cancelado');
        }
    });
}
function confirmar(mensaje) {
    return Swal.fire({
        title: '¿Estás seguro de que deseas ' + mensaje + '?',
        text: 'Esta acción es irreversible.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí'
    }).then((result) => {
        return result.isConfirmed;
    });
}

function confirmarEliminar(event) {
    const idUsuario = event.currentTarget.getAttribute("data-id");
    confirmar("eliminar").then(function (confirmado) {
        if (confirmado) {
            window.location.href = 'http://localhost:8080/privada/eliminar-usuario/' + idUsuario;
        }
    });
}
function confirmarEliminarMoto(event) {
    const idMoto = event.currentTarget.getAttribute("data-id");
    confirmar("eliminar").then(function (confirmado) {
        if (confirmado) {
            window.location.href = 'http://localhost:8080/privada/eliminar-moto/' + idMoto;
        }
    });
}
function confirmarCancelarQuedada(event) {
    const idQuedada = event.currentTarget.getAttribute("data-id");
    confirmar("cancelar").then(function (confirmado) {
        if (confirmado) {
            window.location.href = 'http://localhost:8080/privada/quedadas/detalle-quedada/cancelar-quedada/' + idQuedada;
        }
    });
}
function establecerFechaMinima() {
    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var formattedTomorrow = tomorrow.toISOString().slice(0, 10);

    document.getElementById("fechaHora").min = formattedTomorrow + "T00:00";
}