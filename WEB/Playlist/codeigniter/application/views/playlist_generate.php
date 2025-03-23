<h5>Générer une Playlist Aléatoire</h5>

<form action="<?= site_url('playlist/generate_random'); ?>" method="post" class="generate-playlist-form">
    <input type="text" name="playlistName" placeholder="Nom de la playlist" required>
    <input type="number" min="0" max="1000" name="numSongs" placeholder="Nombre de chansons" required>
    
        <select name="genre" >
            <option value="">Tous les genres</option>
            <?php foreach($genres as $genre): ?>
                <option value="<?= $genre->name ?>"><?= $genre->name ?></option>
            <?php endforeach; ?>
        </select>
        <button type="submit">Générer</button>
</form>

<a href="<?= site_url('playlist'); ?>" class="btn btn-secondary">Retour aux playlists</a>
