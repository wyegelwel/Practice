import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Queue;


public class WordLadders {
  private List<String> _words;
  private String _startWord;
  private String _endWord;

  public WordLadders(List<String> words, String startWord, String endWord){
    _words = words;
    _startWord = startWord;
    _endWord = endWord;
  }

  public static void findAddNeighbors(MapNode wordNode, List<String> wordList){
    if(!wordNode._visited){
      for(String s : wordList){
        if(desiredDiff(wordNode._word, s)){
          MapNode newNode = new MapNode(s);
          newNode._previous = wordNode;
          wordNode.addNeighbor(newNode);
        }
      }
    }
  }

  public static boolean desiredDiff(String word1, String word2){
    //Check for one letter difference
    int desiredDiff = 1;
    int counter = 0;

    for(int i=0; i<word1.length(); i++){
      if(word1.charAt(i) != word2.charAt(i)){
        counter = counter+1;
      }
      //Don't iterate if we've already past the desired difference
      if(counter>desiredDiff){
        return false;
      }
    }
    return counter == desiredDiff;
  }

  public static List<String> sanitizeWords(List<String> words){
    List<String> temp;
    //Only keep words that have the same length as the starting word
    for(String s : words){
      if(s.length() == _startWord.length()){
        //Add the lowercase version of the word to the list
        temp.add(s.toLowerCase());
      }
    }
    return temp;
  }

  public static List<String> resolveWordLadder(List<String> words, String startWord, String endWord){
    if(startWord.length() == endWord.length()){
      List<String> sanitized = sanitizeWords(words);
      return bfs(sanitized, startWord, endWord);
    }
  }

  public static List<MapNode> bfs(List<String> words, String startWord, String endWord){
    LinkedList<String> resultList;

    //Set up our first node
    MapNode startNode = new MapNode(startWord);

    //Set up our queue
    Queue<MapNode> queue = new LinkedList<MapNode>();
    queue.add(startNode);

    while(true){
      MapNode head = queue.poll();
      if(head == null){
        break;
      }
      if(head._word == endWord){
        break;
      }
      else{
        findAddNeighbors(head, words);
        head._visited = true;
        for(MapNode n : head._neighbors){
          queue.add(n);
        }
      }
    }
    while(head!=null){
      resultList.add(head._word);
      head=head._previous;
    }
    return resultList;
  }

  private static class MapNode{
    public final String _word;
    public boolean _visited;
    public MapNode _previous;
    public List<MapNode> _neighbors;

    public MapNode(String word){
      _word = word;
    }

    public static boolean addNeighbor(MapNode node){
      _neighbors.add(node);
    }
  }

  public static void main(String[] args){
    List<String> words;
    String startWord = "Human";
    String endWord = "Hunan";
    System.out.println(WordLadders.resolveWordLadder(words, startWord, endWord));
  }
}
