<h5>Mes Playlists</h5>

<!-- Formulaire pour créer la playlist avec le nom voulu -->
<form action="<?= site_url('playlist/create'); ?>" method="post" class="create-playlist-form">
    <input type="text" name="name" placeholder="Nom de la playlist" required>
    <button type="submit">Créer</button>
</form>

<form action="<?= site_url('playlist/generate_random'); ?>" method="get" class="generate-playlist-form">
    <button type="submit">Générer une Playlist</button>
</form>

<section class="playlists">
    <?php foreach($playlists as $playlist): ?>
        <div>
            <article>
                <header class="short-text">
                    <?= anchor("playlist/view/{$playlist->id}", "{$playlist->name}"); ?>
                </header>
                <form action="<?= site_url('playlist/delete/' . $playlist->id); ?>" method="post" class="btn-supp" style="display:inline;">
                    <button type="submit">Supprimer</button>
                </form>
            </article>
        </div>
    <?php endforeach; ?>
</section>

