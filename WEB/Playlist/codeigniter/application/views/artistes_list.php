<h5>Artistes list</h5>

<div class="sorting-search">
<form action="<?= site_url('Artistes/tri'); ?>" method="get" class="tri-form">
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
    <form action="<?= site_url('Artistes/search'); ?>" method="get" class="search-form">
        <input type="text" name="query" placeholder="Chercher des artistes" class="search-input">
        <button type="submit" class="search-button">Rechercher</button>
    </form>
	
	<?php if(isset($is_search) && $is_search): ?>
        <form action="<?= site_url('Artistes'); ?>" method="get" class="back-form">
            <button type="submit" class="back-button">Retour à la liste complète</button>
        </form>
    <?php endif; ?>
</div>



        
<section class="list">
<?php
$artistAlbums = array();

foreach ($artistes as $artist) {

    if (!array_key_exists($artist->artistName, $artistAlbums)) {
        $artistAlbums[$artist->artistName] = array();
    }
    $artistAlbums[$artist->artistName][] = array(
        'albumName' => $artist->albumName,
        'albumId' => $artist->albumId,
        'year' => $artist->year
    );
}

foreach ($artistAlbums as $artistName => $albums) {
    echo "<div><article>";
    echo "<header class='short-text'>";
    echo "<h2>$artistName</h2>";
    echo "</header>";
    echo "<ul>";
    foreach ($albums as $album) {
        echo  anchor("music/view/{$album['albumId']}", $album['albumName']) . " - " . $album['year'] ;
        echo "<br>";
        echo "<button class='ajout-artistes' onclick=\"location.href='" . site_url("playlist/choix_playlist/{$album['albumId']}") . "'\">Ajouter</button>";
        echo "<br>";
    }
    echo "</ul>";
    echo "</article></div>";
}
?>
</section>
