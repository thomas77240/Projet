<h5>Choisir une Playlist :</h5>
<section class="list">
    <form action="<?= site_url('playlist/add_track') ?>" method="post">
        <input type="hidden" name="songId" value="<?= $songId ?>">
        <select name="playlistId">
            <?php foreach ($playlists as $playlist): ?>
                <option value="<?= $playlist->id ?>"><?= $playlist->name ?></option>
            <?php endforeach; ?>
        </select>
        <button type="submit">Ajouter à la Playlist</button>
    </form>
</section>
