package project.first.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.first.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }


//    public List<Item> findAll(){
//        return em.createQuery("select i from Item i JOIN i.member m", Item.class).getResultList();
//    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

//    public Item findOneUser(Long id){
//        return em.createQuery("select i from Item i JOIN i.member m where i.id = :id", Item.class)
//                .setParameter("id",id).getSingleResult();
//    }

    public Item findOneUser(Long id){
        return em.createQuery("select i from Item i where i.id = :id", Item.class)
                .setParameter("id",id).getSingleResult();
    }


}
