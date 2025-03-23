<h5>Liste des Musiques de l'Album</h5>
<section class="list">
    <?php foreach ($musics as $music): ?>
        <div>
            <article>
                <ul class="musics-album">
                    <li><?= $music->songName ?></li>
                    <li>Durée: <?= $music->trackDuration ?> secondes</li>
                </ul>
                    <form action="<?= site_url('playlist/choose_playlist/' . $music->songId) ?>" method="get">
                        <button class='ajout-album-list'type="submit">Ajouter à la Playlist</button>
                    </form>
            </article>
        </div>
    <?php endforeach; ?>
</section>
