<!doctype html>
<html lang="en" class="has-navbar-fixed-top">
  <head>
    <meta charset="UTF-8" />
    <title>MUSIC APP</title>
    <link
     rel="stylesheet"
     href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css"
      />

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <?=link_tag('assets/style.css')?>
  </head>
  <body>
    <main class='container'>
      <nav>
	<ul>
	  <li id="titre"><strong>Music APP</strong></li>
	</ul>
	
     <ul class="option">
     <li><?=anchor('albums','Albums');?></li>
          <li><?=anchor('artistes','Artistes');?></li>
          <?php if ($this->session->userdata('pseudo')) : /* Vérifier si l'utilisateur est connecté */ ?>
        <li><?=anchor('playlist','Playlist');?></li>
    <?php endif; ?>
              <?php if ($this->session->userdata('pseudo')) : /* Vérifier si l'utilisateur est connecté */ ?>
              <li class="deroulant"><a><?= $this->session->userdata('pseudo'); ?></a>
              <ul class="sous">

         <li><?=anchor('deconnexion', 'Se déconnecter'); ?></li>
         </ul>
         </li>
<?php else : ?>
         <li><?=anchor('connexion', 'Se connecter'); ?></li>
<?php endif; ?>
                </ul>
      </div>
  </nav>
  </body>
</html>
