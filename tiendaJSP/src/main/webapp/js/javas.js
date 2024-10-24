
        function switchTab(tab) {
            // Toggle tab active state
            document.querySelectorAll('.tab').forEach(function(element) {
                element.classList.remove('active');
            });
            document.querySelectorAll('.form-container').forEach(function(element) {
                element.classList.remove('active');
            });

            // Activate the selected tab and form
            document.querySelector('.tab[onclick="switchTab(\'' + tab + '\')"]').classList.add('active');
            document.getElementById(tab + '-form').classList.add('active');
        }
  