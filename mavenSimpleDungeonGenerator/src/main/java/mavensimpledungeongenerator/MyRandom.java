
package mavensimpledungeongenerator;

/**
 * Random class. Source https://rosettacode.org/wiki/Linear_congruential_generator.
 *
 *
 */
public class MyRandom {

    long seed;
    long multiplier;
    long increment;
    long mod;

    
    public MyRandom() {
        this.mod = 2147483647;
        this.mod++;
        this.seed = System.currentTimeMillis() % mod;
        this.multiplier = 214013;
        this.increment = 2531011;
    }

    /**
     * This method returns random numder between 0 - 32767.
     *
     * 
     */
    public int nextInt() {
        seed = (seed * multiplier + increment) % mod;
        return (int) (seed >> 16); 
    }

    /**
     * This method returns random numder between 0 and given parameter.
     *
     * 
     * @param max int
     */
    public int nextInt(int max) {
        return (int) (nextInt() % max);
    }

    public long getSeed() {
        return seed;
    }

}