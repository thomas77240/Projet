<h5>Albums list</h5>
<div class="sorting-search">
<form action="<?= site_url('Albums/tri'); ?>" method="get" class="tri-form">
        <select name="Ctri" class="tri-select">
            <option value="">Trier</option>
            <option value="ASC"<?php echo isset($_GET['Ctri']) && $_GET['Ctri'] == 'ASC' ? ' selected' : ''; ?>>Trie A-Z</option>
			<option value="DESC"<?php echo isset($_GET['Ctri']) && $_GET['Ctri'] == 'DESC' ? ' selected' : ''; ?>>Trie Z-A</option>
			<option value="year ASC"<?php echo isset($_GET['Ctri']) && $_GET['Ctri'] == 'year ASC' ? ' selected' : ''; ?>>Trie par année (croissant)</option>
        	<option value="year DESC"<?php echo isset($_GET['Ctri']) && $_GET['Ctri'] == 'year DESC' ? ' selected' : ''; ?>>Trie par année (décroissant)</option>
        </select>
        <button type="submit" class="tri-button">Appliquer le tri</button>
    </form>
    </ul>
    <form action="<?= site_url('Albums/search'); ?>" method="get" class="search-form">
        <select name="genre" class="search-genre">
            <option value="">Tous les genres</option>
            <?php foreach($genres as $genre): ?>
                <option value="<?= $genre->id ?>"><?= $genre->name ?></option>
            <?php endforeach; ?>
        </select>
        <input type="text" name="query" placeholder="Chercher des albums" class="search-input">
        <button type="submit" class="search-button">Rechercher</button>
    </form>
	
	<?php if(isset($is_search) && $is_search): ?>
        <form action="<?= site_url('Albums'); ?>" method="get" class="back-form">
            <button type="submit" class="back-button">Retour à la liste complète</button>
        </form>
    <?php endif; ?>
</div>

<?php if(isset($is_search) && $is_search): ?>
    <p>Nombre de résultats : <?php echo $num_results; ?></p>
<?php endif; ?>
        
<section class="list">
<?php
foreach($albums as $album){
    echo "<div><article>";
    echo "<header class='short-text'>";
    echo anchor("music/view/{$album->id}", "{$album->name}");
    echo "<button class='ajout-albums' onclick=\"location.href='" . site_url("playlist/choix_playlist/{$album->id}") . "'\">Ajouter</button>";
    echo "</header>";
    echo '<img src="data:image/jpeg;base64,'.base64_encode($album->jpeg).'" />';
    echo "<footer class='short-text'>{$album->year} - {$album->artistName}</footer>
      </article></div>";
}
?>
</section>
