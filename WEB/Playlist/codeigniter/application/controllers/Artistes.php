<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Artistes extends CI_Controller {

    private $sort = 'Tri'; // Variable pour définir l'ordre de tri par défaut
    
	public function __construct(){
		parent::__construct();
		$this->load->model('model_music_artistes');
		$this->load->helper('html');
		$this->load->helper('url');
		$this->load->helper('form');
	}
    
	public function index(){
		$artistes = $this->model_music_artistes->getArtistes(); // recuperer tout les artistes
		$this->load->view('layout/header');
		$this->load->view('artistes_list', ['artistes'=>$artistes]);
		$this->load->view('layout/footer');
	}

    public function tri(){
        $Ctri = $this->input->get('Ctri'); // récupération du critère de trie depuis l'url
        $trie = $this->model_music_artistes->get_tri_Artistes($Ctri);
        $num_results = count($trie);
        $this->load->view('layout/header');
        $this->load->view('artistes_list', [
            'artistes' => $trie, 
            'sort' => $this->sort, 
            'num_results' => $num_results, 
            'is_search' => false
        ]);
        $this->load->view('layout/footer');
    }

    public function search(){
        $query = $this->input->get('query');
        $artistes = $this->model_music_artistes->searchArtistes($query);
        $num_results = count($artistes);
        $this->load->view('layout/header');
        $this->load->view('artistes_list', [
            'artistes' => $artistes, 
            'sort' => $this->sort, 
            'num_results' => $num_results, 
            'is_search' => true
        ]);
        $this->load->view('layout/footer');
    }
}

