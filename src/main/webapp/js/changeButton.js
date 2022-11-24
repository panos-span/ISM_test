btn = document.getElementById("button")
search = document.getElementById("search")
search.addEventListener('change', (event) => {
    if (search.value !== "") {
        btn.textContent = "Edit";
    } else {
        btn.textContent = "Insert";
        window.location.href = 'manageCustomer.jsp';
    }
});