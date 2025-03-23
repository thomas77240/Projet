<h5>Résultats de la recherche pour : <?= $this->input->get('query'); ?></h5>

<form action="<?= site_url('playlist/search_song'); ?>" method="get" class="search-song-form">
    <input type="text" name="query" placeholder="Nom de la chanson" required>
    <button type="submit">Rechercher</button>
</form>

<section class="songs">
<?php foreach($songs as $song): ?>
    <div>
        <article>
            <header class="short-text">
                <?= $song->name; ?>
                <form action="<?= site_url('playlists/add_song'); ?>" method="post" style="display:inline;">
                    <input type="hidden" name="songId" value="<?= $song->id; ?>">
                    <select name="playlistId" required>
                        <?php foreach($playlists as $playlist): ?>
                            <option value="<?= $playlist->id; ?>"><?= $playlist->name; ?></option>
                        <?php endforeach; ?>
                    </select>
                    <button type="submit">Ajouter à la playlist</button>
                </form>
            </header>
        </article>
    </div>
<?php endforeach; ?>
</section>
