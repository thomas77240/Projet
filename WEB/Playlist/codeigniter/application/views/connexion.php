<section class="connexion">

     <div class="authentification">

     <h3> Déjà abonné </h3>
     
     <form action="authentifier" method="POST">

     <label for="email">Email:</label>
     <input type="email" id="email" name="email" placeholder="Entrez votre email" required>
     
     <label for="password">Mot de passe:</label>
     <input type="password" id="password" name="password" placeholder="Entrez votre mot de passe" required>
     
    <button type="submit">Connexion</button>
     
     </form>
     <?php if(isset($error_msg)): ?>
     <div class="error_msg"><?= $error_msg ?></div>
<?php endif; ?>
     
     </div>

     <div class="new">

     <h3> Création de compte </h3>
     
     <form action="traitement" method="POST">
     <label for="nom">Votre nom*</label>
     <input type="text" id="nom" name="nom" placeholder="Entrez votre nom" required>

     <label for="prenom">Votre prénom*</label>
     <input type="text" id="prenom" name="prenom" placeholder="Entrez votre prénom" required>

     <label for="pseudo">Votre pseudo*</label>
     <input type="text" id="pseudo" name="pseudo" placeholder="Entrez votre pseudo" required>

     <label for="email">Votre email*</label>
     <input type="email" id="email" name="email" placeholder="Entrez votre adresse email" required>

     <label for="pass">Votre mot de passe*</label>
     <input type="password" id="pass" name="pass" minlength="8" title="8 caractères minimum" placeholder="Entrez votre mot de passe" required>

     <input type="submit" value="M'inscrire" name="ok">
    
     </form>

<?php if(isset($confirmation_message)): ?>
     <div class="confirmation-message"><?= $confirmation_message ?></div>
<?php endif; ?>
     
     </div>
</section>

	
