<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Model_music_artistes extends CI_Model {

    public function __construct(){
        $this->load->database();
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

    public function get_tri_Artistes($Ctri){
		$orderBy = '';
		if ($Ctri == 'year ASC') {
			$orderBy = 'ORDER BY year ASC';
		} elseif ($Ctri == 'year DESC') {
			$orderBy = 'ORDER BY year DESC';
		} elseif ($Ctri == 'ASC' || $Ctri == 'DESC') {
			$orderBy = "ORDER BY artist.name $Ctri";
		}
	
		$query = $this->db->query(
			"SELECT artist.id AS artistId, artist.name AS artistName, album.name AS albumName, album.id AS albumId, album.year, cover.jpeg
            FROM album
            INNER JOIN artist ON album.artistId = artist.id
            JOIN cover ON cover.id = album.coverId
			$orderBy"
		);
		return $query->result();
	}


	public function searchArtistes($query){
		// Requête SQL principale pour la recherche d'artistes par nom
		$sql = "SELECT artist.id AS artistId, artist.name AS artistName, album.name AS albumName, album.id AS albumId, album.year, cover.jpeg
			FROM album
			JOIN artist ON album.artistId = artist.id
			JOIN cover ON cover.id = album.coverId
			WHERE artist.name LIKE ?
			GROUP BY artist.name, album.year
			ORDER BY artist.name ASC";
	
		$query = $this->db->query($sql, ["%{$query}%"]);
		return $query->result();
	}	
}
