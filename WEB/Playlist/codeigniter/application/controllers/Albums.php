<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Albums extends CI_Controller {

    private $sort = 'Tri'; // Variable privée pour définir l'ordre de tri par défaut

    public function __construct(){
        parent::__construct();
        $this->load->model('model_music');
        $this->load->helper('html');
        $this->load->helper('url');
        $this->load->helper('form');
    }

    // on affiche tous les albums
    public function index(){
        $albums = $this->model_music->getAlbums();
        $genres = $this->model_music->getGenres();
        $num_results = count($albums); // nombre total d'alubm recup
        $this->load->view('layout/header');
        $this->load->view('albums_list', [
            'albums' => $albums, 
            'sort' => $this->sort, 
            'num_results' => $num_results, 
            'is_search' => false,
            'genres' => $genres
        ]);
        $this->load->view('layout/footer');
    }

    // afficher les détail d'un album spécifique (les musiques de l'albums)
    public function view($id){ 
        $tracks = $this->model_music->getTracksByAlbumId($id); // recuperer les pistes d'un album par son id
        $this->load->view('layout/header');
        $this->load->view('album_info', ['tracks' => $tracks]);
        $this->load->view('layout/footer');
    }

    // trié en fonction d'un critère
    public function tri(){
        $Ctri = $this->input->get('Ctri'); // critère de trie via l'url
        $trie = $this->model_music->get_tri_Albums($Ctri); 
        $genres = $this->model_music->getGenres();
        $num_results = count($trie);
        $this->load->view('layout/header');
        $this->load->view('albums_list', [
            'albums' => $trie, 
            'sort' => $this->sort, 
            'num_results' => $num_results, 
            'is_search' => false,
            'genres' => $genres
        ]);
        $this->load->view('layout/footer');
    }

    public function search(){
        $query = $this->input->get('query');
        $genre = $this->input->get('genre');
        $albums = $this->model_music->searchAlbums($query, $genre);
        $genres = $this->model_music->getGenres();
        $num_results = count($albums);
        $this->load->view('layout/header');
        $this->load->view('albums_list', [
            'albums' => $albums, 
            'sort' => $this->sort, 
            'num_results' => $num_results, 
            'is_search' => true,
            'genres' => $genres
        ]);
        $this->load->view('layout/footer');
    }
}


