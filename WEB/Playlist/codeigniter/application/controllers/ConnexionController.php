<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class ConnexionController extends CI_Controller {


    public function __construct() {
        parent::__construct();
        $this->load->helper(array('url', 'html'));
        $this->load->library('session'); 
    }

    public function connexion() {
        $this->load->view('layout/header');
        $this->load->view('connexion');
        $this->load->view('layout/footer');
    }

    public function authentifier() {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            $email = $_POST['email']; // recuère email depuis formulaire
            $password = $_POST['password']; // idem mdp
            
            if (!empty($email) && !empty($password)) { 
                $this->load->database();
                // Utilisation d'une requête préparée
                $query = $this->db->query("SELECT * FROM users WHERE email = ?", array($email));
                $result = $query->row(); // Récupérer la première ligne de résultat
                
                if ($result) { // Vérifier si l'utilisateur existe
                    if (password_verify($password, $result->mdp)) { // Vérifier si le mot de passe est correct
                        $this->session->set_userdata('user_id', $result->id);
                        $this->session->set_userdata('pseudo', $result->pseudo);
                        redirect('../index.php');
                    } else {
                        $data['error_msg'] = "Email ou mot de passe incorrect.";
                    }
                } else {
                    $data['error_msg'] = "Email ou mot de passe incorrect.";
                    
                }
            }
    
            $this->load->view('layout/header');
            $this->load->view('connexion', isset($data) ? $data : []);
            $this->load->view('layout/footer');
        }
    }



    public function traitement() {
        if(isset($_POST['ok'])){ // verifie si le formulaire a été envoyé
            $this->load->database();
    

            $prenom = ucfirst(strtolower($this->input->post('prenom')));
            $nom = strtoupper($this->input->post('nom'));
            $pseudo = $this->input->post('pseudo');
            $mdp = $this->input->post('pass');
            $mdpcrypte = password_hash($mdp, PASSWORD_DEFAULT); // cryptage du mot de passe
            $email = $this->input->post('email');
            $data = array(
                'pseudo' => $pseudo,
                'nom' => $nom,
                'prenom' => $prenom,
                'mdp' => $mdpcrypte,
                'email' => $email
            );
    
            $this->db->insert('users', $data);
            $data['confirmation_message'] = "Inscription réussie ! Vous êtes maintenant inscrit.";
            $this->load->view('layout/header');
            $this->load->view('connexion', $data);
            $this->load->view('layout/footer');
        }
    }

    public function deconnexion() {
        $this->session->unset_userdata('pseudo'); 
        $this->session->sess_destroy(); 
        redirect('../index.php');
    }
}
