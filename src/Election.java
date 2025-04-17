import java.util.*;

class Election {
    private PriorityQueue<Candidate> maxHeap;
    private Map<String, Candidate> candidateMap;
    private int totalVotes;
    private int p;

    private class Candidate implements Comparable<Candidate> {
        String name;
        int votes;

        public Candidate(String name) {
            this.name = name;
            this.votes = 0;
        }

        @Override
        public int compareTo(Candidate other) {
            return Integer.compare(other.votes, this.votes);
        }
    }

    public void initializeCandidates(LinkedList<String> candidates) {
        maxHeap = new PriorityQueue<>();
        candidateMap = new HashMap<>();
        totalVotes = 0;

        for (String name : candidates) {
            Candidate c = new Candidate(name);
            maxHeap.offer(c);
            candidateMap.put(name, c);
        }
    }

    public void castVote(String candidate) {
        if (candidateMap.containsKey(candidate)) {
            Candidate c = candidateMap.get(candidate);
            maxHeap.remove(c);
            c.votes++;
            maxHeap.offer(c);
            totalVotes++;
        }
    }

    public void castRandomVote() {
        if (candidateMap.isEmpty()) return;

        Random rand = new Random();
        List<String> names = new ArrayList<>(candidateMap.keySet());
        String randomCandidate = names.get(rand.nextInt(names.size()));
        castVote(randomCandidate);
    }

    public void rigElection(String candidate) {
        if (!candidateMap.containsKey(candidate)) return;

        // Reset all votes to 0
        for (Candidate c : candidateMap.values()) {
            maxHeap.remove(c);
            c.votes = 0;
            maxHeap.offer(c);
        }

        Candidate toWin = candidateMap.get(candidate);
        maxHeap.remove(toWin);
        toWin.votes = p;
        maxHeap.offer(toWin);
        totalVotes = p;
    }

    public List<String> getTopKCandidates(int k) {
        List<String> topK = new ArrayList<>();
        List<Candidate> temp = new ArrayList<>();

        while (k > 0 && !maxHeap.isEmpty()) {
            Candidate c = maxHeap.poll();
            topK.add(c.name);
            temp.add(c);
            k--;
        }

        // Restore the heap
        for (Candidate c : temp) {
            maxHeap.offer(c);
        }

        return topK;
    }

    public void auditElection() {
        List<Candidate> allCandidates = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            allCandidates.add(maxHeap.poll());
        }

        for (Candidate c : allCandidates) {
            System.out.println(c.name + " - " + c.votes);
        }

        // Restore the heap
        for (Candidate c : allCandidates) {
            maxHeap.offer(c);
        }
    }
}

class ElectionSystem {
    public static void main(String[] args) {
        Election election = new Election();
        LinkedList<String> candidates = new LinkedList<>(Arrays.asList(
                "Marcus Fenix", "Dominic Santiago", "Damon Baird", "Cole Train", "Anya Stroud"));
        election.initializeCandidates(candidates);
        int p = 5;

        election.castVote("Cole Train");
        election.castVote("Cole Train");
        election.castVote("Marcus Fenix");
        election.castVote("Anya Stroud");
        election.castVote("Anya Stroud");

        System.out.println("Top 3 candidates after 5 votes: " + election.getTopKCandidates(3));

        election.rigElection("Marcus Fenix");
        System.out.println("Top 3 candidates after rigging the election: " + election.getTopKCandidates(3));

        System.out.println("Audit results:");
        election.auditElection();
    }
}