//Extra credit
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class RandomizedElectionSystem {
    public static void main(String[] args) {
        Election election = new Election();

        String[] possibleNames = {"Alice", "Bob", "Charlie", "David", "Eve",
                "Frank", "Grace", "Heidi", "Ivan", "Judy"};
        int numCandidates = ThreadLocalRandom.current().nextInt(3, possibleNames.length + 1);
        LinkedList<String> candidates = new LinkedList<>();

        List<String> nameList = new ArrayList<>(Arrays.asList(possibleNames));
        Collections.shuffle(nameList);
        for (int i = 0; i < numCandidates; i++) {
            candidates.add(nameList.get(i));
        }

        election.initializeCandidates(candidates);
        int p = ThreadLocalRandom.current().nextInt(5, 20);

        System.out.println("Randomized election with " + numCandidates +
                " candidates and " + p + " votes:");
        System.out.println("Candidates: " + candidates);

        for (int i = 0; i < p; i++) {
            election.castRandomVote();
        }

        int k = Math.min(3, numCandidates);
        System.out.println("Top " + k + " candidates after " + p + " votes: " +
                election.getTopKCandidates(k));

        if (ThreadLocalRandom.current().nextBoolean()) {
            String candidateToRig = candidates.get(
                    ThreadLocalRandom.current().nextInt(candidates.size()));
            System.out.println("Rigging election for: " + candidateToRig);
            election.rigElection(candidateToRig);
            System.out.println("Top " + k + " candidates after rigging: " +
                    election.getTopKCandidates(k));
        }

        System.out.println("Final audit results:");
        election.auditElection();
    }
}