class Reroot{
    static private int[][] dp;
    // dp[u][i] is property of subtree of adj[u][i] if tree is rooted at u
    static private ArrayList<Integer>[] adj;
    static private int[] p_index;
    // p_index[u] is index of p(u) in adj[u]

    // extra stuff here

    public static final int[][] compute(ArrayList<Integer>[] adj){ // pass extra stuff as parameters
        Reroot.adj = adj;
        int n = adj.length;
        dp = new int[n][];
        p_index = new int[n];

        // extra stuff here

        for(int i = 0; i < n; ++i) dp[i] = new int[adj[i].size()];
        dfs1(0, -1);
        dfs2(0, -1);
        return dp;
    }
    private static final int dfs1(int u, int p){
        if(u != 0 && adj[u].size() == 1) return leaf_prop(u);
        int rest = 0, neighbors = adj[u].size();
        // rest is property of all child subtrees, change its intial value accordingly
        for(int i = 0; i < neighbors; ++i){
            int v = adj[u].get(i);
            if(v != p) rest = merge(rest, dp[u][i] = dfs1(v, u));
            else p_index[u] = i;
        }
        return combine(u, rest);
    }
    private static final void dfs2(int u, int p){
        if(u != 0 && adj[u].size() == 1) return;
        if(adj[u].size() == 1){ // if root is a leaf node
            int v = adj[0].get(0);
            dp[v][p_index[v]] = leaf_prop(0);
            dfs2(v, 0);
            return;
        }
        else{
            int neighbors = adj[u].size();
            int[] pref_dp = new int[neighbors - 1], suf_dp = new int[neighbors - 1];
            pref_dp[0] = dp[u][0]; suf_dp[neighbors - 2] = dp[u][neighbors - 1];
            for(int i = 1; i < neighbors - 1; ++i){
                pref_dp[i] = merge(pref_dp[i - 1], dp[u][i]);
                suf_dp[neighbors - 2 - i] = merge(suf_dp[neighbors - 1 - i], dp[u][neighbors - 1 - i]);
            }
            for(int i = 0; i < neighbors; ++i){
                int v = adj[u].get(i);
                if(v != p){
                    dp[v][p_index[v]] = combine(u, i == 0 ? suf_dp[0] : i == neighbors - 1 ? pref_dp[neighbors - 2] : merge(pref_dp[i - 1], suf_dp[i]));
                    dfs2(v, u);
                }
            }
        }
    }
    // CHANGE THESE FUNCTIONS
    private static final int leaf_prop(int u){
        // return what property of subtree of u of would be knowing that u is a leaf node
    }
    private static final int merge(int x, int y){
        // return merging of properties x and y as 1 property
    }
    private static final int combine(int u, int rest){
        // return property of subtree of u knowing that rest is merging of properties of all its child subtrees
    }
}