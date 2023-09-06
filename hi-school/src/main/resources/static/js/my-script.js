 function confirmAction(action) {
        return confirm("Are you sure you want to " + action + "?");
    }

    function confirmAndDelete(url) {
        if (confirmAction('delete')) {
            fetch(url, { method: 'GET' })
                .then(response => {
                    if (response.ok) {
                        window.location.reload(); // Reload trang sau khi xóa
                    }
                });
        }
    }

