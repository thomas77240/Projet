<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Music extends CI_Controller {

    public function __construct(){
        parent::__construct();
        $this->load->model('model_music');
        $this->load->helper('html');
        $this->load->helper('url');
        $this->load->helper('form');
    }

    public function index(){
        $musics = $this->model_music->getMusics();
        $this->load->view('layout/header');
        $this->load->view('musics_list', ['musics' => $musics]);
        $this->load->view('layout/footer');
    }

    public function view($albumId){
        // Récupérer les musiques pour un album spécifique
        $musics = $this->model_music->getMusicsByAlbum($albumId);
        $this->load->view('layout/header');
        $this->load->view('album_musics', ['musics' => $musics]);
        $this->load->view('layout/footer');
    }
}
