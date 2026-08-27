"""
Generate high-contrast 300 DPI diagrams for D.A.V. Zone-F Blueprint exam papers.
"""

import os
import matplotlib.pyplot as plt
import numpy as np

IMG_DIR = r"C:\Users\rajan\.gemini\antigravity\scratch\math_exam_2026\images"
os.makedirs(IMG_DIR, exist_ok=True)

plt.rcParams['font.family'] = 'DejaVu Sans'
plt.rcParams['font.size'] = 11
plt.rcParams['font.weight'] = 'bold'
plt.rcParams['axes.edgecolor'] = '#000000'
plt.rcParams['axes.linewidth'] = 1.5


def save_fig(fig, filename):
    filepath = os.path.join(IMG_DIR, filename)
    fig.patch.set_facecolor('white')
    plt.tight_layout()
    plt.savefig(filepath, dpi=300, facecolor='white', edgecolor='none', bbox_inches='tight')
    plt.close(fig)
    print(f"Saved: {filename}")


def gen_set_a_q19():
    """Graph of f(x) = |x - 2|"""
    fig, ax = plt.subplots(figsize=(4.8, 3.2))
    ax.set_facecolor('#FFFFFF')
    x = np.linspace(-1, 5, 400)
    y = np.abs(x - 2)
    ax.plot(x, y, color='#002B66', lw=3.0, label=r'$f(x) = |x - 2|$')
    ax.plot(2, 0, 'ro', markersize=8)
    ax.annotate('Corner at (2, 0)\nContinuous but\nNOT differentiable', xy=(2, 0), xytext=(2.2, 1.2),
                arrowprops=dict(facecolor='black', shrink=0.08, width=1.5, headwidth=6),
                fontweight='bold', fontsize=9.5, bbox=dict(boxstyle="square,pad=0.3", fc="#FFF9E6", ec="#CC9900", lw=1.2))
    ax.axhline(0, color='black', lw=1.5)
    ax.axvline(0, color='black', lw=1.5)
    ax.grid(True, linestyle='--', color='#CCCCCC', alpha=0.8)
    ax.set_xlabel('x', fontweight='bold', fontsize=11)
    ax.set_ylabel('y', fontweight='bold', fontsize=11)
    ax.set_title('Figure 19: Continuity vs Differentiability of |x - 2|', fontsize=11, fontweight='bold', pad=8)
    ax.set_ylim(-0.5, 3.5)
    ax.legend(loc='upper left', framealpha=0.95, facecolor='white', edgecolor='black')
    save_fig(fig, 'set_a_q19.png')


def gen_set_a_q20():
    """Graph of f(x) = -(x - 3)^2 + 4"""
    fig, ax = plt.subplots(figsize=(4.8, 3.2))
    ax.set_facecolor('#FFFFFF')
    x = np.linspace(0.5, 5.5, 400)
    y = -(x - 3)**2 + 4
    ax.plot(x, y, color='#006622', lw=3.0, label=r'$f(x) = -(x-3)^2 + 4$')
    ax.plot(3, 4, 'ro', markersize=8)
    ax.annotate('Local Maximum (3, 4)\n$f\'(3)=0, f\'\'(3) < 0$', xy=(3, 4), xytext=(1.0, 2.3),
                arrowprops=dict(facecolor='black', shrink=0.08, width=1.5, headwidth=6),
                fontweight='bold', fontsize=9.5, bbox=dict(boxstyle="square,pad=0.3", fc="#EAF7EA", ec="#006622", lw=1.2))
    ax.axhline(0, color='black', lw=1.5)
    ax.axvline(0, color='black', lw=1.5)
    ax.grid(True, linestyle='--', color='#CCCCCC', alpha=0.8)
    ax.set_xlabel('x', fontweight='bold', fontsize=11)
    ax.set_ylabel('y', fontweight='bold', fontsize=11)
    ax.set_title('Figure 20: Local Maximum at (3, 4)', fontsize=11, fontweight='bold', pad=8)
    ax.set_ylim(-1, 5)
    ax.legend(loc='lower center', framealpha=0.95, facecolor='white', edgecolor='black')
    save_fig(fig, 'set_a_q20.png')


def gen_set_a_q36():
    """Case Study 1: Equivalence Classes Partition on A = {1, 2, 3, 4, 5}"""
    fig, ax = plt.subplots(figsize=(5.5, 3.0))
    ax.set_facecolor('#FFFFFF')
    
    rect_main = plt.Rectangle((0.5, 0.4), 9.0, 4.0, fill=False, edgecolor='#000000', lw=2)
    ax.add_patch(rect_main)
    ax.text(0.8, 4.0, 'Set A = {1, 2, 3, 4, 5}', fontsize=12, fontweight='bold', color='#000000')
    
    ellipse1 = plt.matplotlib.patches.Ellipse((3.0, 2.2), 3.8, 2.4, color='#E6F0FA', ec='#004C99', lw=2.5)
    ax.add_patch(ellipse1)
    ax.text(3.0, 3.0, 'Odd Class [1] = {1, 3, 5}', ha='center', fontsize=10, fontweight='bold', color='#004C99')
    ax.plot([2.0, 3.0, 4.0], [2.0, 2.3, 1.9], 'o', color='#004C99', markersize=9)
    ax.text(2.0, 1.5, '1', ha='center', fontweight='bold', fontsize=12, color='#000000')
    ax.text(3.0, 1.8, '3', ha='center', fontweight='bold', fontsize=12, color='#000000')
    ax.text(4.0, 1.4, '5', ha='center', fontweight='bold', fontsize=12, color='#000000')

    ellipse2 = plt.matplotlib.patches.Ellipse((7.0, 2.2), 3.2, 2.4, color='#EAF7EA', ec='#006600', lw=2.5)
    ax.add_patch(ellipse2)
    ax.text(7.0, 3.0, 'Even Class [2] = {2, 4}', ha='center', fontsize=10, fontweight='bold', color='#006600')
    ax.plot([6.3, 7.7], [2.0, 2.0], 'o', color='#006600', markersize=9)
    ax.text(6.3, 1.5, '2', ha='center', fontweight='bold', fontsize=12, color='#000000')
    ax.text(7.7, 1.5, '4', ha='center', fontweight='bold', fontsize=12, color='#000000')

    ax.set_xlim(0, 10)
    ax.set_ylim(0, 4.8)
    ax.axis('off')
    ax.set_title('Figure 36: Partition of Set A into Equivalence Classes', fontsize=11, fontweight='bold', pad=6)
    save_fig(fig, 'set_a_q36.png')


def gen_set_a_q37():
    """Case Study 2: Matrices Award Scheme Infographic"""
    fig, ax = plt.subplots(figsize=(5.5, 3.2))
    ax.set_facecolor('#FFFFFF')
    
    # Table layout
    table_data = [
        ['School', 'Discipline (x)', 'Cleanliness (y)', 'Regularity (z)', 'Total Fund'],
        ['School P', '3', '2', '4', '₹ 4,300'],
        ['School Q', '4', '1', '3', '₹ 3,800']
    ]
    
    table = ax.table(cellText=table_data, loc='center', cellLoc='center')
    table.auto_set_font_size(False)
    table.set_fontsize(10.5)
    table.scale(1.0, 1.8)
    
    # Style header row
    for col in range(5):
        cell = table[(0, col)]
        cell.set_facecolor('#003366')
        cell.set_text_props(color='white', weight='bold')
    
    # Style data rows
    for row in range(1, 3):
        for col in range(5):
            cell = table[(row, col)]
            cell.set_facecolor('#F0F4F8' if row == 1 else '#FFFFFF')
            cell.set_edgecolor('#003366')
            cell.set_text_props(weight='bold')
            
    ax.axis('off')
    ax.set_title('Figure 37: Prize Allocation Scheme for Schools P & Q', fontsize=11, fontweight='bold', pad=12)
    save_fig(fig, 'set_a_q37.png')


def gen_set_a_q38():
    """Case Study 3: Revenue & Cost Curve Graph"""
    fig, ax = plt.subplots(figsize=(5.0, 3.2))
    ax.set_facecolor('#FFFFFF')
    x = np.linspace(0, 15, 300)
    r = 3*x**2 + 36*x + 5
    ax.plot(x, r, color='#006622', lw=3.0, label=r'Total Revenue $R(x)$')
    
    # Point at x = 10
    r_10 = 3*(10**2) + 36*10 + 5
    ax.plot(10, r_10, 'ko', markersize=8)
    ax.annotate(r'$x = 10, R(10) = 665$' + '\n' + r'$MR(10) = ₹96$', xy=(10, r_10), xytext=(2.0, 500),
                arrowprops=dict(facecolor='black', shrink=0.08, width=1.5, headwidth=6),
                fontweight='bold', fontsize=9.5, bbox=dict(boxstyle="square,pad=0.3", fc="#EAF7EA", ec="#006622", lw=1.2))
    
    ax.set_xlabel('Cars Sold ($x$)', fontweight='bold', fontsize=11)
    ax.set_ylabel('Total Revenue $R(x)$ (₹ Thousands)', fontweight='bold', fontsize=11)
    ax.set_title('Figure 38: Revenue Analysis Curve', fontsize=11, fontweight='bold', pad=8)
    ax.grid(True, linestyle='--', color='#CCCCCC', alpha=0.8)
    ax.set_xlim(0, 15)
    ax.set_ylim(0, 1000)
    ax.legend(loc='upper left', framealpha=0.95, facecolor='white', edgecolor='black')
    save_fig(fig, 'set_a_q38.png')


def gen_set_b_q19():
    """Graph of f(x) = [x] (Greatest Integer Function)"""
    fig, ax = plt.subplots(figsize=(4.8, 3.2))
    ax.set_facecolor('#FFFFFF')
    for n in range(-2, 4):
        ax.plot([n, n+1], [n, n], color='#002B66', lw=3.0)
        ax.plot(n, n, 'o', color='#002B66', markersize=7)
        ax.plot(n+1, n, 'o', markerfacecolor='white', markeredgecolor='#002B66', markeredgewidth=2.0, markersize=7)
    
    ax.axhline(0, color='black', lw=1.5)
    ax.axvline(0, color='black', lw=1.5)
    ax.grid(True, linestyle='--', color='#CCCCCC', alpha=0.8)
    ax.set_xlabel('x', fontweight='bold', fontsize=11)
    ax.set_ylabel('y = [x]', fontweight='bold', fontsize=11)
    ax.set_title('Figure 19: Discontinuity of Greatest Integer Function', fontsize=11, fontweight='bold', pad=8)
    ax.set_xlim(-2.5, 4.5)
    ax.set_ylim(-2.5, 4.5)
    save_fig(fig, 'set_b_q19.png')


def gen_set_b_q20():
    """Graph of f(x) = ln(x) for x > 0"""
    fig, ax = plt.subplots(figsize=(4.8, 3.2))
    ax.set_facecolor('#FFFFFF')
    x = np.linspace(0.1, 5, 300)
    y = np.log(x)
    ax.plot(x, y, color='#006622', lw=3.0, label=r'$f(x) = \ln(x)$')
    ax.plot(1, 0, 'ro', markersize=8)
    ax.annotate('(1, 0)', xy=(1, 0), xytext=(1.2, -0.8), fontweight='bold', fontsize=10)
    ax.annotate(r'$f\'(x) = \frac{1}{x} > 0$' + '\n(Strictly Increasing)', xy=(2.5, np.log(2.5)), xytext=(1.6, 0.8),
                arrowprops=dict(facecolor='black', shrink=0.08, width=1.5, headwidth=5),
                fontweight='bold', fontsize=9.5, bbox=dict(boxstyle="square,pad=0.3", fc="#EAF7EA", ec="#006622", lw=1.2))
    ax.axhline(0, color='black', lw=1.5)
    ax.axvline(0, color='black', lw=1.5)
    ax.grid(True, linestyle='--', color='#CCCCCC', alpha=0.8)
    ax.set_xlabel('x', fontweight='bold', fontsize=11)
    ax.set_ylabel('y', fontweight='bold', fontsize=11)
    ax.set_title('Figure 20: Strictly Increasing Nature of ln(x)', fontsize=11, fontweight='bold', pad=8)
    ax.set_xlim(-0.5, 5)
    ax.set_ylim(-2.5, 2.0)
    ax.legend(loc='lower right', framealpha=0.95, facecolor='white', edgecolor='black')
    save_fig(fig, 'set_b_q20.png')


def gen_set_b_q36():
    """Case Study 1 (Set B): Divisibility Relation on A = {1, 2, 3, 4, 6}"""
    fig, ax = plt.subplots(figsize=(5.5, 3.2))
    ax.set_facecolor('#FFFFFF')
    
    pos = {
        '1': (2.5, 0.6),
        '2': (1.3, 1.8),
        '3': (3.7, 1.8),
        '4': (1.3, 3.0),
        '6': (3.7, 3.0)
    }
    
    edges = [
        ('1', '2'), ('1', '3'), ('1', '4'), ('1', '6'),
        ('2', '4'), ('2', '6'),
        ('3', '6')
    ]
    
    for u, v in edges:
        x1, y1 = pos[u]
        x2, y2 = pos[v]
        ax.annotate('', xy=(x2, y2), xytext=(x1, y1),
                    arrowprops=dict(arrowstyle='->', color='#003366', lw=2.0, shrinkA=14, shrinkB=14))
    
    for node, (x, y) in pos.items():
        circle = plt.Circle((x, y), 0.30, color='#004C99', ec='black', lw=2.0)
        ax.add_patch(circle)
        ax.text(x, y, node, color='white', ha='center', va='center', fontweight='bold', fontsize=13)

    ax.set_xlim(0, 5)
    ax.set_ylim(0, 3.6)
    ax.axis('off')
    ax.set_title('Figure 36: Divisibility Relation Directed Graph on A = {1, 2, 3, 4, 6}', fontsize=11, fontweight='bold', pad=6)
    save_fig(fig, 'set_b_q36.png')


def gen_set_b_q37():
    """Case Study 2 (Set B): Matrices Award Scheme Infographic"""
    fig, ax = plt.subplots(figsize=(5.5, 3.2))
    ax.set_facecolor('#FFFFFF')
    
    table_data = [
        ['School', 'Honesty (x)', 'Hard Work (y)', 'Cooperation (z)', 'Total Fund'],
        ['School A', '4', '3', '2', '₹ 3,700'],
        ['School B', '5', '2', '4', '₹ 4,600']
    ]
    
    table = ax.table(cellText=table_data, loc='center', cellLoc='center')
    table.auto_set_font_size(False)
    table.set_fontsize(10.5)
    table.scale(1.0, 1.8)
    
    for col in range(5):
        cell = table[(0, col)]
        cell.set_facecolor('#800020')
        cell.set_text_props(color='white', weight='bold')
    
    for row in range(1, 3):
        for col in range(5):
            cell = table[(row, col)]
            cell.set_facecolor('#FFF5F5' if row == 1 else '#FFFFFF')
            cell.set_edgecolor('#800020')
            cell.set_text_props(weight='bold')
            
    ax.axis('off')
    ax.set_title('Figure 37: Prize Allocation Scheme for Schools A & B', fontsize=11, fontweight='bold', pad=12)
    save_fig(fig, 'set_b_q37.png')


def gen_set_b_q38():
    """Case Study 3 (Set B): Total Revenue Function Curve R(x) = 13x^2 + 26x + 15"""
    fig, ax = plt.subplots(figsize=(5.0, 3.2))
    ax.set_facecolor('#FFFFFF')
    x = np.linspace(0, 10, 300)
    r = 13*x**2 + 26*x + 15
    ax.plot(x, r, color='#CC5500', lw=3.0, label=r'Total Revenue $R(x)$')
    
    r_7 = 13*(7**2) + 26*7 + 15
    ax.plot(7, r_7, 'ko', markersize=8)
    ax.plot([7, 7], [0, r_7], 'k--', lw=1.5)
    ax.plot([0, 7], [r_7, r_7], 'k--', lw=1.5)
    ax.annotate(r'$x = 7, R(7) = ₹834$' + '\n' + r'$MR(7) = ₹208 > 0$', xy=(7, r_7), xytext=(1.5, 950),
                arrowprops=dict(facecolor='black', shrink=0.08, width=1.5, headwidth=6),
                fontweight='bold', fontsize=9.5, bbox=dict(boxstyle="square,pad=0.3", fc="#FFF5E6", ec="#CC5500", lw=1.2))
    
    ax.set_xlabel('Units Sold ($x$)', fontweight='bold', fontsize=11)
    ax.set_ylabel('Total Revenue $R(x)$ in ₹', fontweight='bold', fontsize=11)
    ax.set_title('Figure 38: Total Revenue Curve R(x)', fontsize=11, fontweight='bold', pad=8)
    ax.grid(True, linestyle='--', color='#CCCCCC', alpha=0.8)
    ax.set_xlim(0, 10)
    ax.set_ylim(0, 1600)
    ax.legend(loc='upper left', framealpha=0.95, facecolor='white', edgecolor='black')
    save_fig(fig, 'set_b_q38.png')


def main():
    print("Generating diagrams for D.A.V. Blueprint...")
    gen_set_a_q19()
    gen_set_a_q20()
    gen_set_a_q36()
    gen_set_a_q37()
    gen_set_a_q38()
    
    gen_set_b_q19()
    gen_set_b_q20()
    gen_set_b_q36()
    gen_set_b_q37()
    gen_set_b_q38()
    print("All diagrams generated successfully.")


if __name__ == '__main__':
    main()
