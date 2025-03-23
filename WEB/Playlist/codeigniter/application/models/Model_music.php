<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Model_music extends CI_Model {
    public function __construct(){
        $this->load->database();
    }

    public function getAlbums(){
        $query = $this->db->query(
            "SELECT album.name, album.id, year, artist.name as artistName, genre.name as genreName, jpeg 
            FROM album 
            JOIN artist ON album.artistid = artist.id
            JOIN genre ON genre.id = album.genreid
            JOIN cover ON cover.id = album.coverid
            ORDER BY year"
        );
        return $query->result();
    }

    public function get_tri_Albums($Ctri){
		$orderBy = '';
		if ($Ctri == 'year ASC') {
			$orderBy = 'ORDER BY year ASC';
		} elseif ($Ctri == 'year DESC') {
			$orderBy = 'ORDER BY year DESC';
		} elseif ($Ctri == 'ASC' || $Ctri == 'DESC') {
			$orderBy = "ORDER BY album.name $Ctri";
		}
	
		$query = $this->db->query(
			"SELECT album.name, album.id, year, artist.name as artistName, genre.name as genreName, jpeg 
			FROM album 
			JOIN artist ON album.artistid = artist.id
			JOIN genre ON genre.id = album.genreid
			JOIN cover ON cover.id = album.coverid
			$orderBy"
		);
		return $query->result();
	}


    public function getArtistes(){
        $query = $this->db->query(
            "SELECT artist.id AS artistId, artist.name AS artistName, album.name AS albumName, album.id AS albumId, album.year, cover.jpeg
            FROM album
            INNER JOIN artist ON album.artistId = artist.id
            JOIN cover ON cover.id = album.coverId
            GROUP BY artist.name, album.year"
        );
        return $query->result();
    }

    public function getMusics(){
        $query = $this->db->query(
            "SELECT 
                track.id AS trackId,
                track.number AS trackNumber,
                track.duration AS trackDuration,
                track.diskNumber AS diskNumber,
                album.id AS albumId,
                album.name AS albumName,
                album.year AS albumYear,
                artist.name AS artistName,
                song.id AS songId,
                song.name AS songName,
                cover.jpeg AS coverImage
            FROM track
            JOIN album ON track.albumId = album.id
            JOIN artist ON album.artistId = artist.id
            JOIN song ON track.songId = song.id
            JOIN cover ON album.coverId = cover.id
            ORDER BY album.id, track.diskNumber, track.number"
        );
        return $query->result();
    }

    public function getMusicsByAlbum($albumId){
        $query = $this->db->query(
            "SELECT 
                track.id AS trackId,
                track.number AS trackNumber,
                track.duration AS trackDuration,
                track.diskNumber AS diskNumber,
                album.id AS albumId,
                album.name AS albumName,
                artist.name AS artistName,
                song.id AS songId, 
                song.name AS songName
            FROM track
            JOIN album ON track.albumId = album.id
            JOIN artist ON album.artistId = artist.id
            JOIN song ON track.songId = song.id
            WHERE album.id = ?
            ORDER BY track.diskNumber, track.number",
            array($albumId) );
        return $query->result();
    }

	public function getGenres(){
		$query = $this->db->query("SELECT * FROM genre");
		return $query->result();
	}

    // vérifier que la playlist spécifié appartient à l'utilisateur connecté actuellement
    public function playlistOfUser($id){
        $user_id = $this->session->userdata('user_id');
        $this->db->select('id');
        
        $this->db->from('playlist');
        $this->db->where('userId', $user_id);
        $this->db->where('id', $id);

        $query = $this->db->get();

        return $query->num_rows() > 0;
    }


	
    public function searchAlbums($query, $genre) {
        // Sélection des colonnes
        $this->db->select('album.name, album.id, year, artist.name as artistName, genre.name as genreName, jpeg');
        
        // Tables et jointures
        $this->db->from('album');
        $this->db->join('artist', 'album.artistid = artist.id');
        $this->db->join('genre', 'genre.id = album.genreid');
        $this->db->join('cover', 'cover.id = album.coverid');
        
        // Conditions de recherche
        $this->db->group_start();
        $this->db->like('album.name', $query);
        $this->db->or_like('artist.name', $query);
        $this->db->group_end();
        
        // Condition supplémentaire par genre si spécifié
        if (!empty($genre)) {
            $this->db->where('genre.id', $genre);
        }
        
        // Tri par défaut
        $this->db->order_by('album.id', 'ASC');
        
        // Exécution de la requête
        $query = $this->db->get();
        
        // Renvoi des résultats
        return $query->result();
    }

    public function createPlaylist($name, $userId) {
        $data = array(
            'name' => $name,
            'userId' => $userId 
        );
        $this->db->insert('playlist', $data); // Insère les données dans la table 'playlist'
        return $this->db->insert_id(); // Renvoie l'ID de la dernière insertion
    }
    public function deletePlaylist($playlistId) {
        $this->db->delete('playlist', array('id' => $playlistId));
        $this->db->delete('playlistsong', array('playlistId' => $playlistId));
    }

    // récupère playlist d'un utilisateur avec userid
    public function getPlaylistsByUser($userId) {
        $query = $this->db->get_where('playlist', array('userId' => $userId));
        return $query->result();
    }

    // récupère playlist par id de playlist
    public function getPlaylistById($playlistId) {
        $query = $this->db->get_where('playlist', array('id' => $playlistId), 1);
        return $query->row(); // Renvoie la première ligne trouvée (la playlist correspondant à l'ID)
    }

    public function addSongToPlaylist($playlistId, $songId) {
        $data = array(
            'playlistId' => $playlistId,
            'songId' => $songId
        );
        return $this->db->insert('playlistsong', $data);
    }

    public function removeSongFromPlaylist($playlistId, $songId) {
        $this->db->delete('playlistsong', array('playlistId' => $playlistId, 'songId' => $songId));
    }

    public function getSongsByPlaylist($playlistId) {
        $this->db->select('song.*');
        $this->db->from('playlistsong');
        $this->db->join('song', 'playlistsong.songId = song.id');
        $this->db->where('playlistsong.playlistId', $playlistId);
        $query = $this->db->get();
        return $query->result();
    }

    public function findSongByName($songName) {
        $query = $this->db->get_where('song', array('name' => $songName));
        return $query->row(); 
    }

    public function generate_random_playlist($numSongs, $playlistName, $userId, $genre = '') {
        $this->db->select('song.id'); 
        $this->db->from('song');
        $this->db->join('track', 'track.songId = song.id');
        $this->db->join('album', 'track.albumId = album.id');
        $this->db->join('genre', 'album.genreId = genre.id');
        if (!empty($genre)) {
            $this->db->where('genre.name', $genre);
        }
        $this->db->order_by('RAND()'); 
        $this->db->limit($numSongs); 
    
        $query = $this->db->get();
        // Récupère les chansons sélectionnées aléatoirement
        $songs = $query->result();
        // Crée une nouvelle playlist avec le nom spécifié
        $playlistId = $this->createPlaylist($playlistName, $userId);
        // Ajoute chaque chanson à la playlist créée
        foreach ($songs as $song) {
            $this->addSongToPlaylist($playlistId, $song->id);
        }
        redirect('playlist/view/'.$playlistId); 
    } 
}