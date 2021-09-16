$(document).ready(function () {
});

async function vacunado() {
    const request = await fetch('http://localhost:8080/api/vacunas', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        }
    });
    const vacunas = await request.json();
    let estadoVacuna = document.getElementById("estado_vacuna")
    if (estadoVacuna.checked == true) {
        let divHTML = '<label>Tipo de vacuna</label><select class="form-select" aria-label="Default select example">'
        + '<option selected>Selecciona el tipo de vacuna</option>';
        for (let vacuna of vacunas) {
            let vacunasHTML = '<option value="' + vacuna.id + '">' + vacuna.tipo_vacuna + '</option>';
            divHTML += vacunasHTML;
        }
        divHTML += '<div class="wrap-input100 validate-input" data-validate="Ingresa la fecha de vacunación">'
        +'<label>Fecha de vacunación</label>'
        +'<input class="input100" type="date" data-date-format="YYYY MMMM DD" name="fecha_vacuna" id="fecha_vacuna">'
        +'<span class="focus-input100" data-placeholder=""></span>'
        +'</div><label>Número de dosis</label>'
        +'<select class="form-select" aria-label="Default select example">'
        +'<option selected>Selecciona número de dosis</option>'
        +'<option value="1">1</option>'
        +'<option value="2">2</option>'

        $('#mas').append(divHTML);

    }else{
        $('#mas').append('');
    }


}
function registrar(){
    let usuario =[]
    let confirm = false;
    usuario.fecha_nacimiento = document.getElementById("fecha_nacimiento").value;
    usuario.direccion = document.getElementById("direccion").value;
    usuario.celular = document.getElementById("celular").value;
    usuario.estadoVacuna = document.getElementById("estado_vacuna").checked;
    if(usuario.estadoVacuna){
        usuario.tipo_vacuna = document.getElementById("tipo_vacuna").value;
        usuario.fecha_vacuna = document.getElementById("fecha_vacuna").value;
        usuario.dosis = document.getElementById("dosis").value;
        if(usuario.fecha_nacimiento.length==0 ||usuario.direccion.length==0 || usuario.celular.length==0 ){ 
            alert ('Debe completar todos los campos')
        }else{
            confirm=true;
        }
    }else{
        if(usuario.fecha_nacimiento.length==0 ||usuario.direccion.length==0 || usuario.celular.length==0 || usuario.tipo_vacuna.length==0
            || usuario.fecha_vacuna.length==0 || usuario.dosis.length==0 ){ 
                alert ('Debe completar todos los campos sobre la vacuna')
            }else{
                confirm=true;
            }
    }
    if(confirm){
        alert ('Se registró')

    }


}
