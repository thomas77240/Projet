<h5>Choisir une Playlist :</h5>
<section class="list">
    <form action="<?= site_url('playlist/add_album_to_playlist') ?>" method="post">
        <input type="hidden" name="albumId" value="<?= $albumId ?>">
        <select name="playlistId">
            <?php foreach ($playlists as $playlist): ?>
                <option value="<?= $playlist->id ?>"><?= $playlist->name ?></option>
            <?php endforeach; ?>
        </select>
        <button type="submit">Ajouter toutes les chansons à la Playlist</button>
    </form>
</section>
