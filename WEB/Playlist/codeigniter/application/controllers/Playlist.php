<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Playlist extends CI_Controller {

    public function __construct(){
        parent::__construct();
        $this->load->model('model_music');
        $this->load->helper('html');
        $this->load->helper('url');
        $this->load->helper('form');
        // verifier si il y a une sessions utilisateur en cours
        if (!$this->session->userdata('user_id')) {
            redirect('connexion');
        }
    }

    public function index(){
        $userId = $this->session->userdata('user_id');
        // recupère toutes les playlist de l'utilisateur
        $playlists = $this->model_music->getPlaylistsByUser($userId);
        $this->load->view('layout/header');
        $this->load->view('playlist_list', ['playlists' => $playlists]);
        $this->load->view('layout/footer');
    }

    public function create(){
        // récupère le nom choisis dans le formulaire
        $name = $this->input->post('name');
        $userId = $this->session->userdata('user_id');
        $this->model_music->createPlaylist($name, $userId);
        redirect('playlist');
    }

    public function delete($playlistId){
        $this->model_music->deletePlaylist($playlistId);
        redirect('playlist');
    }

    public function duplicate($id) {
        // récupère données de la playlist grâce à son id
        $playlist = $this->model_music->getPlaylistById($id);
        if ($playlist) {
            $newName = $playlist->name . '_bis';
            $userId = $this->session->userdata('user_id');
            $newPlaylistId = $this->model_music->createPlaylist($newName, $userId); // création de nouvelle playlist avec les modifs
            $songs = $this->model_music->getSongsByPlaylist($id); // récupère tout les sons de la playlist
            foreach ($songs as $song) { 
                $this->model_music->addSongToPlaylist($newPlaylistId, $song->id);
            }
            redirect('playlist/view/' . $newPlaylistId);
        } else {
            echo "Playlist non trouvée.";
        }
    }

    public function view($id) {
        // vérifier si la playlist apartient à l'utilisateur actuel
        if($this->model_music->playlistOfUser($id)){
            $songs = $this->model_music->getSongsByPlaylist($id); // récupère les chansons
            $playlist = $this->model_music->getPlaylistById($id); // récupère les données 
            if ($playlist) {
                $data['playlistName'] = $playlist->name; 
                $data['songs'] = $songs;
                $data['playlistId'] = $id;
                $this->load->view('layout/header');
                $this->load->view('playlist_view', $data);
                $this->load->view('layout/footer');
            } else {
                echo "Playlist non trouvée.";
            }
        }else{
            redirect('playlist');
        }
        
        
    }

    public function add_song(){
        $playlistId = $this->input->post('playlistId');
        $songId = $this->input->post('songId');
        $this->model_music->addSongToPlaylist($playlistId, $songId);
        redirect('playlists/view/' . $playlistId);
    }
    

    public function remove_song(){
        $playlistId = $this->input->post('playlistId');
        $songId = $this->input->post('songId');
        $this->model_music->removeSongFromPlaylist($playlistId, $songId);
        redirect('playlist/view/' . $playlistId);
    }

    public function search_song(){
        // Récupération de l'ID de la playlist et du nom de la chanson depuis le formulaire
        $playlistId = $this->input->post('playlistId');
        $songName = $this->input->post('songName');
        // Recherche de la chanson par nom dans la base de données
        $song = $this->model_music->findSongByName($songName);
        if ($song) {
            $this->model_music->addSongToPlaylist($playlistId, $song->id);
        }
        redirect('playlist/view/' . $playlistId);
    }

    public function choose_playlist($songId) {
    // Récupération de toutes les playlists de l'utilisateur actuel
    $playlists = $this->model_music->getPlaylistsByUser($this->session->userdata('user_id'));
    $this->load->view('layout/header');
    $this->load->view('choose_playlist', ['playlists' => $playlists, 'songId' => $songId]);
    $this->load->view('layout/footer');
}


public function choix_playlist($albumId) {
    $playlists = $this->model_music->getPlaylistsByUser($this->session->userdata('user_id'));
    $this->load->view('layout/header');
    $this->load->view('choix_playlist', ['playlists' => $playlists, 'albumId' => $albumId]);
    $this->load->view('layout/footer');
}

    // Ajoute une chanson à une playlist spécifiée par formulaire
    public function add_track() {
        $songId = $this->input->post('songId');
        $playlistId = $this->input->post('playlistId');
        if (!empty($songId) && !empty($playlistId)) {
            $this->model_music->addSongToPlaylist($playlistId, $songId);
            redirect('playlist/view/' . $playlistId);
        } else {
            echo "Erreur : Veuillez sélectionner une playlist.";
        }
    }

    public function add_album_to_playlist() {
        $albumId = $this->input->post('albumId');
        $playlistId = $this->input->post('playlistId');
        $songs = $this->model_music->getMusicsByAlbum($albumId);
        foreach ($songs as $song) {
            $this->model_music->addSongToPlaylist($playlistId, $song->songId);
        }
        redirect('playlist/view/' . $playlistId);
    }

  
    public function generate_random(){
        $genres = $this->model_music->getGenres();
        $this->load->view('layout/header');
        $this->load->view('playlist_generate', ['genres' => $genres]);
        $this->load->view('layout/footer');

        $genre = $this->input->post('genre');
        $numSongs = (int)$this->input->post('numSongs');
        $playlistName = $this->input->post('playlistName');
        $userId = $this->session->userdata('user_id');
    
        if ($numSongs > 0 && !empty($playlistName)) {
            $playlistId = $this->model_music->generate_random_playlist($numSongs, $playlistName, $userId, $genre);
            redirect('playlist/view/' . $playlistId);
        } else {
            echo "Erreur : Veuillez entrer un nombre de chansons valide et un nom de playlist.";
        }
    }
    
    
}
?>
