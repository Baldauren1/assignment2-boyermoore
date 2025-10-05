import pandas as pd
import matplotlib.pyplot as plt
import os

os.makedirs('docs/performance-plots', exist_ok=True)

df = pd.read_csv('metrics_boyer_ns.csv')
summary = df.groupby(['n'])['time_ns'].agg(['mean','median','std','count']).reset_index()
summary = summary.rename(columns={'mean':'avg_ns','median':'median_ns','std':'std_ns','count':'count'})
summary.to_csv('docs/performance-plots/boyer_summary_ns.csv', index=False)

plt.figure()
plt.plot(summary['n'], summary['avg_ns'], marker='o')
plt.xlabel('n')
plt.ylabel('avg time (ns)')
plt.title('Boyer-Moore avg time vs n (ns)')
plt.xscale('log')
plt.yscale('log')
plt.grid(True)
plt.savefig('docs/performance-plots/time_vs_n_ns.png')
plt.close()

md = summary.set_index('n')[['avg_ns']].round().astype(int)
with open('docs/performance-plots/execution_time_table.md','w') as f:
    f.write(md.to_markdown())

print("Saved summary and plot to docs/performance-plots/")
