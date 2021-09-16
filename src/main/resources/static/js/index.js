async function iniciarSesion(){
    let data={};
    data.usuario = document.getElementById('usuario').value;
    data.clave = document.getElementById('pass').value;
    const request = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(data)
    });
    const response = await request.json();
    if(response[0]==null){
        alert("Las credenciales son incorrectas, por favor intentar de nuevo");
    }else{
        localStorage.token = response[0];
        localStorage.rol = response[1];
        localStorage.id = response[2];
        localStorage.usuario = data.usuario;

               
        if(localStorage.rol== "Administrador"){

            window.location.href = 'adminDashboard.html'
        }else{
            window.location.href = 'empleadoDashboard.html'
        }
        
    }
}
