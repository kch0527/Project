function deleteConfirm(idx){
  if(!confirm("탈퇴 하시겠습니까?")){
    return false;
  }else{
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", `/members/${idx}`, true);
    xhr.onload = function () {
      location.href = `/home`
    }
    xhr.send(null);
  }
}
