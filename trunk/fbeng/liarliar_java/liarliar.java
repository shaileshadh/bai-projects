import java.io.*;
import java.util.*;

public class liarliar{
    public static void main(String[] args) throws Exception{

        BufferedReader in = new BufferedReader(
            new FileReader(args[0]));

        // How many people there are in total, or total number of nodes in the
        // bitartite graph
        int nPeople = Integer.parseInt(in.readLine());
        BipartiteGraph graph = new BipartiteGraph();

        for(int i=0; i<nPeople; i++){

            // Parse this node, and construct graph along with it
            StringTokenizer objLine = new StringTokenizer(in.readLine());
            String name = objLine.nextToken();
            int nLiars = Integer.parseInt(objLine.nextToken());

            for(int j=0; j<nLiars; j++){
                String liar = in.readLine();
                graph.connect(name,liar);
            }
        }

        boolean[] truths = graph.solve();
        int trues = 0, liars = 0;
        // Tally up trues and liars
        for(boolean b : truths){
            if(b) trues++;
            else liars++;
        }

        if(trues < liars){
            int temp = trues;
            trues = liars;
            liars = temp;
        }

        System.out.println(trues + " " + liars);
    }
}

/* Undirected graph backed by a Hashmap and an ArrayList. It is only possible to
   add elements to the graph by specifying the start and end node. */
class BipartiteGraph{
    Map<String,List<String>> graph = new HashMap<String,List<String>>();
    Map<String,Integer> intMap = new HashMap<String,Integer>();
    Map<Integer,String> nameMap = new HashMap<Integer,String>();
    int count = 0;

    BipartiteGraph(){}

    /* Create a connection between two nodes */
    void connect(String a, String b){

        if(!intMap.containsKey(a)){
            intMap.put(a,count);
            nameMap.put(count,a);
            count++;
        }

        // Retrieve the already existing list or create a new one.
        List<String> already = graph.get(a);
        if(already == null) already = new ArrayList<String>();

        if(already.contains(b)) return;

        already.add(b);
        graph.put(a,already);

        connect(b,a);
    }

    /* Turn the string based graph into an int-based graph */
    Map<Integer,List<Integer>> makeGraph(){
        Map<Integer,List<Integer>> ret = new HashMap<Integer,List<Integer>>();
        Set<Map.Entry<String,List<String>>> entrySet = graph.entrySet();

        for(Map.Entry<String,List<String>> entry : entrySet){
            String sKey = entry.getKey();
            List<String> sList = entry.getValue();

            List<Integer> iList = new ArrayList<Integer>();
            for(String s : sList) iList.add(intMap.get(s));

            ret.put(intMap.get(sKey), iList);
        }

        return ret;
    }

    /* Fill the bipartite graph if possible. */
    boolean[] solve(){
        Map<Integer,List<Integer>> gr = makeGraph();

        boolean[] truthTable = new boolean[count];
        // keep track of which ones we've visited
        boolean[] visited = new boolean[count];
        Queue<Integer> queue = new LinkedList<Integer>();
        truthTable[0] = true; // assume the first person tells the truth
        queue.add(0);

        // Breadth first search on graph
        while(!queue.isEmpty()){
            int next = queue.remove();
            boolean truth = truthTable[next];
            List<Integer> list = gr.get(next);

            // Go through list and toggle when needed
            for(int i=0; i<list.size(); i++){
                int node = list.get(i);
                if(!visited[node]){
                    visited[node] = true;
                    truthTable[node] = !truth;
                    queue.add(node);
                }
            }
        }

        return truthTable;
    }
}
