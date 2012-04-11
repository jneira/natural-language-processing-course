import java.util.*;

public class LaplaceBigramLanguageModel implements LanguageModel {
  
  /** Initialize your data structures in the constructor. */
  public LaplaceBigramLanguageModel(HolbrookCorpus corpus) {
    train(corpus); 
  }
  
  /** Takes a corpus and trains your language model. 
    * Compute any counts or other corpus statistics in this function.
    */
  public void train(HolbrookCorpus corpus) { 
    for(Sentence sentence : corpus.getData()) { // iterate over sentences
      for(int i=0;i<sentence.size();i++) { // iterate over words
        Datum datum = sentence.get(i);
        unigramTrain(datum.getWord());
        if(i>1) {
          Datum prev=sentence.get(i-1);  
          bigramTrain(prev.getWord(),datum.getWord()); 
        }
      }
    }
  }
 
  HashMap<ArrayList<String>,Integer> biFreqs=
      new HashMap<ArrayList<String>,Integer>();
  public void bigramTrain(String prev,String actual) {
    ArrayList<String> bigram=getBigram (prev,actual);
    Integer freq = biFreqs.get(bigram);
    if (freq==null) {
      biFreqs.put(bigram,1);
    }else{
      biFreqs.put(bigram,freq+1);
    }
  }

  HashMap<String,Integer> uniFreqs=
      new HashMap<String,Integer>();
  public void unigramTrain(String word) {
    Integer freq = uniFreqs.get(word);
    if (freq==null) {
      uniFreqs.put(word,1);
    }else{
      uniFreqs.put(word,freq+1);
    }
  }

  public ArrayList<String> getBigram(String w1,String w2) {
    ArrayList<String> ws=new ArrayList<String>();
    ws.add(w1);
    ws.add(w2);
    return ws;
  }

  public void prn (String str) {
    System.out.println(str);
  }
  /** Takes a list of strings as argument and returns the log-probability of the 
    * sentence using your language model. Use whatever data you computed in train() here.
    */
  public double score(List<String> sentence) {
    double score=0.0;
    int V=uniFreqs.size();
    for(int i=0;i<sentence.size();i++) { // iterate over words
      if(i>0) {
        String actual=sentence.get(i);
        String previo=sentence.get(i-1);
        ArrayList<String> bigram=getBigram(previo,actual);
        Integer bigramFreq=biFreqs.get(bigram);
        if (bigramFreq==null) bigramFreq=0;
        Integer unigramFreq=uniFreqs.get(previo);
        if (unigramFreq==null) unigramFreq=1;
        double probability=(bigramFreq+1.0)/(unigramFreq+V);
        score+=Math.log(probability);
      }
    }
    return score;
  }
}
