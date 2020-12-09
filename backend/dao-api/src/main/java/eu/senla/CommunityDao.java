package eu.senla;

public interface CommunityDao extends GenericDao<Community, Integer> {
    Community findByName(String name);
}
