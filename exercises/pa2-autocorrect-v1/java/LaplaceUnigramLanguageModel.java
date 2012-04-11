import java.util.List;
import java.util.HashMap;

public class LaplaceUnigramLanguageModel implements LanguageModel {

  /** Initialize your data structures in the constructor. */
  public LaplaceUnigramLanguageModel(HolbrookCorpus corpus) {
    train(corpus);
  }

  /** Takes a corpus and trains your language model. 
    * Compute any counts or other corpus statistics in this function.
    */

  HashMap<String,Integer> frequencies=new HashMap<String,Integer>();
  Integer numWords=0;
  public void train(HolbrookCorpus corpus) {
    for(Sentence sentence : corpus.getData()) { // iterate over sentences
      for(Datum datum : sentence) { // iterate over words
        String word = datum.getWord(); // get the actual word
        Integer freq=frequencies.get(word);
        numWords++;
        if (freq==null) {
            frequencies.put(word,1);
        }else{
            frequencies.put(word,freq+1);
        }
      }
    }
  }

  /** Takes a list of strings as argument and returns the log-probability of the 
    * sentence using your language model. Use whatever data you computed in train() here.
    */
  public double score(List<String> sentence) {
    double score=0.0;
    for(String word : sentence) { // iterate over words in the sentence
      Integer freq=frequencies.get(word);
      if (freq==null) {
        freq=0;
      }
      double probability=Math.log(freq+1.0/(numWords*2));
      score += probability;
    }
    return score;
  }
}

