import React, { useEffect, useState } from "react";
import axios from 'axios';
import { IMaskInput } from "react-imask";
import Swal from "sweetalert2";


const Person = () => {
const [persons, setPersons] = useState([]);
const [namePerson, setNamePerson] = useState("");
const [cpfPerson, setCpfPerson] = useState("");
const [addressPerson, setAddressPerson] = useState("");


const [idPersonModal, setIdPersonModal] = useState("");
const [namePersonModal, setNamePersonModal] = useState("");
const [cpfPersonModal, setCpfPersonModal] = useState("");
const [addressPersonModal, setAddressPersonModal] = useState("");


function deletePerson(id, name){
    Swal.fire({
        title: 'Tem certeza que deseja excluir '+ name+ '?',
        showCancelButton: true,
        confirmButtonText: 'Excluir',
      }).then((result) => {
        if (result.isConfirmed) {
            axios.delete('http://127.0.0.1:8080/persons/' + id)
            .then(response => {
                console.log(response);
                Swal.fire('Excluido!', '', 'success');
                showPersons();
            }).catch( error =>{
                console.log(error);
        });
        }
      })
}

function updatePerson(){
    if(!namePersonModal || !cpfPersonModal || !addressPersonModal){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Existem campos sem preencher!',
          })
    }else{
        const person = {
            "name":namePersonModal,
        "address": addressPersonModal,
    "cpf": cpfPersonModal.replace(/\D/g, "")};
            axios.put('http://127.0.0.1:8080/persons/' + idPersonModal ,person)
            .then(response => {
                console.log(response);
                clearUpdatePerson();
                showPersons();
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Pessoa atualizada com sucesso!',
                    showConfirmButton: false,
                    timer: 1500
                  })
            })
            .catch(error =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Ocorreu um erro na atualização!',
                  })
                  clearUpdatePerson();
                console.log(error);
            })
    }
}
function clearUpdatePerson(){
    setIdPersonModal("");
    setNamePersonModal("");
    setCpfPersonModal("");
    setAddressPersonModal("");
}

function savePerson(){
    if(!namePerson || !cpfPerson || !addressPerson){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Existem campos sem preencher!',
          })
    }else{
        const person = {"name":namePerson,
    "address": addressPerson,
"cpf": cpfPerson.replace(/\D/g, "")};
        axios.post('http://127.0.0.1:8080/persons',person)
        .then(response => {
            setAddressPerson("");
            setCpfPerson("");
            setNamePerson("");
            showPersons();
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Pessoa cadastrada com sucesso!',
                showConfirmButton: false,
                timer: 1500
              })
        }).catch(error =>{
            console.log(error);

        });
    }
}


function showModal(id){
axios.get('http://127.0.0.1:8080/persons/' + id ).then(
    response => {
console.log(response.data);
setAddressPersonModal(response.data.address);
setNamePersonModal(response.data.name);
setCpfPersonModal(response.data.cpf);
setIdPersonModal(response.data.id);
    }
).catch(
    error =>{
        console.log(error);
    }
)


}

function showPersons(){
    axios.get('http://127.0.0.1:8080/persons').then(
    response => {
        setPersons(response.data);
    }
).catch(
    error =>{
        console.log(error);
    }
)
}

    useEffect(()=>{
showPersons();
    },[])
    return(
<>
<div >
<h3 className="text-start mr-5 ml-5">Cadastro de Pessoa</h3>
    <div className="mr-5 ml-5 row">
  <div class="mb-3 mt-3 input-group">
    <label for="name" className="col-sm-2 col-form-label">Nome:</label>
    <div className="col-sm-6">
    <input type="text" className="form-control" id="name" value={namePerson} onChange={e => setNamePerson(e.target.value)}/>
    </div>
  </div>

  <div class="mb-3 mt-3 input-group">
    <label for="cpf" className="col-sm-2 col-form-label">CPF:</label>
    <div className="col-sm-6">
    <IMaskInput
  mask="000.000.000-00"
     className="form-control" id="cpf" value={cpfPerson} onChange={e => setCpfPerson(e.target.value)}/>
    </div>
  </div>


  <div class="mb-3 mt-3 input-group">
    <label for="address" className="col-sm-2 col-form-label">Endereço:</label>
    <div className="col-sm-6">
    <input type="text" className="form-control" id="address" value={addressPerson} onChange={e => setAddressPerson(e.target.value)}/>
    </div>
  </div>

  <div className="text-end col-sm-8">
  <button class="btn btn-primary" onClick={savePerson}>Salvar</button>
  </div>
    </div>

</div>

<hr className="bg-dark"/>

<div className="mr-5 ml-5 row">
<h3 className="text-start m-5">Contas Cadastradas</h3>
<div className="col-sm-8">

<table class="table">
  <thead>
    <tr>
      <th scope="col">Nome</th>
      <th scope="col">CPF</th>
      <th scope="col">Endereço</th>
      <th scope="col"></th>
      <th scope="col"></th>
    </tr>
  </thead>
  <tbody>
  {persons.map(person =>(

<tr key={person.id}>
      <td>{person.name}</td>
      <td>{person.cpf}</td>
      <td>{person.address}</td>
      <td><button className="btn btn-warning" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onClick={() => showModal(person.id)}>Editar</button></td>
      <td><button className="btn btn-danger" onClick={() => deletePerson(person.id, person.name)}>Remover</button></td>
    </tr>

  ))}
  </tbody>
</table>
</div>


</div>


{/* <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
  Launch static backdrop modal
</button> */}

<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">Editar Pessoa</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">




      <div className="mr-5 ml-5 row">
  <div class="mb-3 mt-3 input-group">
    <label for="name" className="col-sm-2 col-form-label">Nome:</label>
    <div className="col-sm-10">
    <input type="text" className="form-control" id="name" value={namePersonModal} onChange={e => setNamePersonModal(e.target.value)}/>
    </div>
  </div>

  <div class="mb-3 mt-3 input-group">
    <label for="cpf" className="col-sm-2 col-form-label">CPF:</label>
    <div className="col-sm-10">
    <IMaskInput
  mask="000.000.000-00"
     className="form-control" id="cpf" value={cpfPersonModal} onChange={e => setCpfPersonModal(e.target.value)}/>
    </div>
  </div>


  <div class="mb-3 mt-3 input-group">
    <label for="address" className="col-sm-2 col-form-label">Endereço:</label>
    <div className="col-sm-10">
    <input type="text" className="form-control" id="address" value={addressPersonModal} onChange={e => setAddressPersonModal(e.target.value)}/>
    </div>
  </div>

    </div>



      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onClick={updatePerson}>Salvar Alterações</button>
      </div>
    </div>
  </div>
</div>

</>


    )
}

export default Person;