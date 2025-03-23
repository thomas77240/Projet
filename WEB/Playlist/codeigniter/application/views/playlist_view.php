<h5>Playlist : <?= $playlistName; ?></h5>

<!-- Formulaire pour rechercher et ajouter une chanson -->
<form action="<?= site_url('playlist/search_song'); ?>" method="post" class="add-song-form">
    <input type="hidden" name="playlistId" value="<?= $playlistId; ?>">
    <input type="text" name="songName" placeholder="Nom de la chanson" required>
    <button type="submit">Rechercher et Ajouter</button>
</form>

<!-- Section pour afficher les chansons de son playlist -->
<?php if (!empty($songs)): ?>
     <h5>Chansons actuelles :</h5>
    <section class="list">
            <?php foreach($songs as $song): ?>
                <div><article>
                    <?= $song->name; ?>
                    <!-- Formulaire pour supprimer la chanson de la playlist -->
                    <form action="<?= site_url('playlist/remove_song'); ?>" method="post" style="display:inline;">
                        <input type="hidden" name="playlistId" value="<?= $playlistId; ?>">
                        <input type="hidden" name="songId" value="<?= $song->id; ?>">
                        <button type="submit">Supprimer</button>
                    </form>
                </article></div>
            <?php endforeach; ?>
    </section>
<?php endif; ?>

<a href="<?= site_url('playlist/duplicate/' . $playlistId); ?>" class="btn btn-primary">Dupliquer cette playlist</a>
<a href="<?= site_url('playlist'); ?>" class="btn btn-secondary">Retour à toutes les playlists</a>

