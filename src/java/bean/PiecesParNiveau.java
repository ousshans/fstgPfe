/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Topo
 */
@Entity
public class PiecesParNiveau implements Serializable {

    @OneToMany(mappedBy = "piecesParNiveau")
    private List<PieceEtudiant> piecesFournies;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int nombre;
    @ManyToOne
    private Piece piecesJointe;
    @ManyToOne
    private Niveau niveau;

    public List<PieceEtudiant> getPiecesFournies() {
        return piecesFournies;
    }

    public void setPiecesFournies(List<PieceEtudiant> piecesFournies) {
        this.piecesFournies = piecesFournies;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Piece getPiecesJointe() {
        return piecesJointe;
    }

    public void setPiecesJointe(Piece piecesJointe) {
        this.piecesJointe = piecesJointe;
    }
    

 

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiecesParNiveau)) {
            return false;
        }
        PiecesParNiveau other = (PiecesParNiveau) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.PiecesParSec[ id=" + id + " ]";
    }
    
}