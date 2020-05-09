package algorithm;

import distance.BusDistance;
import distance.SchoolDistance;
import model.Bus;
import model.School;

import java.util.*;

/**
 * dependency analysis between buses capacity and number of pupils belonging to school
 */
public class DependencyAnalysis {

    private Map<School, Integer> schoolOccurrence = new HashMap<>();

    private Map<Bus, List<School>> analysisMap = new HashMap<>();

    Map<Bus, List<School>> analysis(Map<Bus, List<BusDistance>> distanceBetweenBusesAndPupils,
                          Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils) {
        initOccurrence(distanceBetweenSchoolsAndPupils);
        List<School> removeInOthers = new ArrayList<>();
        for (Map.Entry<Bus, List<BusDistance>> mapBus: distanceBetweenBusesAndPupils.entrySet()) {
            List<School> schools = new ArrayList<>();
            int i = 0;
            for (Map.Entry<School, List<SchoolDistance>> mapSchool: distanceBetweenSchoolsAndPupils.entrySet()) {
                if (mapBus.getKey().getCapacity() >= (mapSchool.getValue().size()) ) {
                    schools.add(mapSchool.getKey());
                    i++;
                    addOccurrence(mapSchool.getKey());
                }
            }
            if (i == 1) {
                removeInOthers.add(schools.get(0));
            }
            analysisMap.put(mapBus.getKey(), schools);
        }

        removeWhenOneOccurrence();

        if (removeInOthers.size() > 0)
            removeWhenOneSize(removeInOthers);

        return analysisMap;
    }

    private void removeWhenOneOccurrence() {

        Map<Bus, List<School>> toAdding = new HashMap<>();
        Iterator<Map.Entry<Bus, List<School>>> iterator = analysisMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Bus, List<School>> entry = iterator.next();
            for (Map.Entry<School,Integer> mapSchool : schoolOccurrence.entrySet()) {
                if (mapSchool.getValue() == 1 && entry.getValue().contains(mapSchool.getKey())){
                    toAdding.put(entry.getKey(), new ArrayList<>(Collections.singleton(mapSchool.getKey())));
                    iterator.remove();
                }
            }
        }
        if (!toAdding.isEmpty()) {
            analysisMap.putAll(toAdding);
        }
    }

    private void removeWhenOneSize(List<School> removeInOthers) {
        for ( Map.Entry<Bus, List<School>> map : analysisMap.entrySet()) {
            Iterator<School> iterator = map.getValue().iterator();
            while (iterator.hasNext()) {
                School school2 = iterator.next();
                for (School school : removeInOthers) {
                    if (school.equals(school2) && map.getValue().size() != 1) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void initOccurrence( Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils) {
        for (Map.Entry<School, List<SchoolDistance>> mapSchool: distanceBetweenSchoolsAndPupils.entrySet()) {
            schoolOccurrence.put(mapSchool.getKey(), 0);
        }
    }
    private void addOccurrence(School key) {
        Integer i = schoolOccurrence.get(key) + 1;
        schoolOccurrence.put(key, i);
    }
}
