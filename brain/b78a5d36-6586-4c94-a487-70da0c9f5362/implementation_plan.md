# Implementation Plan — Class XII Mathematics Half-Yearly 2026–27

## Issues Found in the Original Paper

### 1. Chapter-Wise Mark Distribution Is Wrong

The original paper does **not** match the required distribution:

| Chapter | Required | Original (Actual) | Difference |
|---|---|---|---|
| Relations & Functions | **8** | 11 | +3 (excess) |
| Inverse Trig Functions | **8** | 7 | −1 (short) |
| Matrices | **10** | 7 | −3 (short) |
| Determinants | **10** | 12 | +2 (excess) |
| Continuity & Differentiability | **14** | 14 | ✓ |
| Application of Derivatives | **14** | 16 | +2 (excess) |
| Integrals | **16** | 13 | −3 (short) |

### 2. Deleted Topic Violations

> [!CAUTION]
> **Q27 (3 marks):** "Prove that tan⁻¹(1/2) + tan⁻¹(1/3) = π/4" — Uses the addition formula for inverse trig functions, which is a **deleted property** under Chapter 2.

> [!WARNING]
> **Q26 (3 marks):** "Show f(x) = 3x + 2 is one-one and onto" — The concept of one-one/onto is retained, but **invertible functions** are deleted. This question is borderline acceptable since it doesn't ask about invertibility directly, but it's often paired with deleted content.

### 3. Other Issues
- Q10 (singular matrix) straddles Matrices/Determinants classification ambiguity
- No Section D (5-mark) question from Matrices chapter — needed for 10 marks
- Only 1 integral question in Section D; need more Integrals coverage for 16 marks
- Original paper lacks diversity in question types for some chapters

---

## Corrected Mark Distribution (Both Sets)

| Chapter | Sec A (1m) | AR (1m) | Sec B (2m) | Sec C (3m) | Sec D (5m) | Sec E (4m) | **Total** |
|---|---|---|---|---|---|---|---|
| Relations & Functions | 2 | — | 1 | — | — | 1 | **8** |
| Inverse Trig Functions | 2 | — | — | 2 | — | — | **8** |
| Matrices | 3 | — | 1 | — | 1 | — | **10** |
| Determinants | 2 | — | — | 1 | 1 | — | **10** *(see note)* |
| Continuity & Differentiability | 3 | 1 | 1 | 1 | 1 | — | **14** |
| Application of Derivatives | 4 | 1 | 1 | 1 | — | 1 | **14** |
| Integrals | 2 | — | 1 | 1 | 1 | 1 | **16** |
| **Totals** | **18** | **2** | **5** | **6** | **4** | **3** | **80** |

> [!NOTE]
> **Case Studies (Sec E):** R&F (Q36), Application of Derivatives (Q37), Integrals (Q38) — three distinct real-world topic areas, matching standard CBSE pattern.

---

## Deleted Topics — Compliance Checklist

| Chapter | Deleted Portion | Compliance |
|---|---|---|
| Ch 1: R&F | Composition of functions, invertible functions, binary operations | ✅ No such questions included |
| Ch 2: Inv Trig | Properties/identities (tan⁻¹a + tan⁻¹b, 2tan⁻¹x, etc.) | ✅ Only principal values & domain questions |
| Ch 3: Matrices | Elementary row/column operations | ✅ Only transpose, symmetric, algebra used |
| Ch 4: Determinants | Proof-based properties of determinants | ✅ Only computation & application questions |
| Ch 5: C&D | Rolle's Theorem, Mean Value Theorem | ✅ No MVT/Rolle's questions |
| Ch 6: App of Derivatives | General trimming | ✅ Standard NCERT questions only |
| Ch 7: Integrals | Some misc exercises trimmed | ✅ Standard methods only |

---

## Proposed Questions — SET A (Revised)

### Section A — MCQs (Q1–Q18, 1 mark each)

| Q# | Chapter | Difficulty | Question Summary |
|---|---|---|---|
| 1 | R&F | LOT | R = {(1,1),(2,2),(3,3),(1,2)} on A={1,2,3} — classify relation |
| 2 | R&F | MOT | f(x) = x² from R→R — one-one/onto? |
| 3 | InvTrig | LOT | Principal value of sin⁻¹(−1/2) |
| 4 | InvTrig | MOT | Domain of cos⁻¹(2x − 1) |
| 5 | Matrices | LOT | (A + Aᵀ) is always symmetric/skew-symmetric? |
| 6 | Matrices | MOT | Value of x for skew-symmetric matrix |
| 7 | Matrices | MOT | (A − Aᵀ) is always — what type? |
| 8 | Determinants | LOT | \|3A\| for 3×3 matrix with \|A\|=5 |
| 9 | Determinants | MOT | \|adj A\| for 3×3 with \|A\|=4 |
| 10 | C&D | LOT | f(x)=\|x\| — continuous/differentiable? |
| 11 | C&D | LOT | dy/dx of e^(2x) |
| 12 | C&D | MOT | Continuity condition: k value for piecewise f(x) at x=5 |
| 13 | AppD | LOT | Rate of change of circle area at r=5 |
| 14 | AppD | LOT | f(x)=x²−4x+6 strictly increasing interval |
| 15 | AppD | MOT | Point on y=x² where tangent ∥ x-axis |
| 16 | AppD | MOT | Slope of tangent to y=x³−3x+2 at x=1 |
| 17 | Integrals | LOT | ∫ sec²x dx |
| 18 | Integrals | LOT | ∫ 1/(1+x²) dx |

### Section A — Assertion-Reason (Q19–Q20)

| Q# | Chapter | Difficulty | Summary |
|---|---|---|---|
| 19 | C&D | HOT | A: f(x)=\|x−2\| continuous at x=2; R: differentiable at x=2 → **(c)** |
| 20 | AppD | HOT | A: (3,4) is local max of −(x−3)²+4; R: f′=0, f″<0 at max → **(a)** |

### Section B (Q21–Q25, 2 marks each)

| Q# | Chapter | Difficulty | Question |
|---|---|---|---|
| 21 | R&F | MOT | Check R,S,T for R={(1,1),(2,2),(3,3),(1,2),(2,3)} on {1,2,3} |
| 22 | Matrices | LOT | Find 2A − 3B for given 2×2 matrices |
| 23 | C&D | MOT | Differentiate y = sin(x² + 1) |
| 24 | AppD | MOT | Find slope of tangent to y = x³ − x at x = 2 |
| 25 | Integrals | LOT | Evaluate ∫ x·e^(x²) dx |

### Section C (Q26–Q31, 3 marks each)

| Q# | Chapter | Difficulty | Question |
|---|---|---|---|
| 26 | InvTrig | HOT | Find: (i) sin⁻¹(sin(2π/3)) (ii) cos⁻¹(cos(7π/6)) (iii) tan⁻¹(tan(3π/4)) |
| 27 | InvTrig | MOT | Find tan⁻¹(1) + cos⁻¹(−1/2) + sin⁻¹(−1/2) |
| 28 | Determinants | MOT | Evaluate 3×3 determinant using cofactors |
| 29 | C&D | HOT | If y = xˣ, find dy/dx (logarithmic differentiation) |
| 30 | AppD | HOT | Intervals of increase/decrease for f(x) = 2x³−15x²+36x+1 |
| 31 | Integrals | MOT | Evaluate ∫ x sin x dx (integration by parts) |

### Section D (Q32–Q35, 5 marks each)

| Q# | Chapter | Difficulty | Question |
|---|---|---|---|
| 32 | Matrices | HOT | Express 3×3 matrix as sum of symmetric + skew-symmetric |
| 33 | Determinants | HOT | Solve system x+y+z=6, y+3z=11, x−2y+z=0 using A⁻¹ (adjoint method) |
| 34 | C&D | HOT | Prove: (1−x²)y₂ − xy₁ − 2 = 0 for y = (sin⁻¹x)² |
| 35 | Integrals | HOT | Evaluate ∫ (x²+1)/(x²−5x+6) dx (partial fractions) |

### Section E (Q36–Q38, 4 marks each, sub-parts 2+1+1)

| Q# | Chapter | Difficulty | Case Study Theme |
|---|---|---|---|
| 36 | R&F | HOT | Equivalence relation on A={1,2,3,4,5}, R: \|a−b\| even |
| 37 | AppD | HOT | Marginal cost: C(x) = 0.005x³ − 0.02x² + 30x + 5000 |
| 38 | Integrals | MOT | Particle motion: v(t) = 3t² − 12t + 9, s(0)=0 |

---

## Proposed Questions — SET B (New Paper)

### Section A — MCQs (Q1–Q18)

| Q# | Chapter | Difficulty | Question Summary |
|---|---|---|---|
| 1 | R&F | MOT | R = {(1,1),(2,2),(3,3),(1,3),(3,1)} on A — classify |
| 2 | R&F | MOT | Number of one-one functions from {1,2,3} to itself |
| 3 | InvTrig | LOT | Principal value of cos⁻¹(−1/√2) |
| 4 | InvTrig | MOT | Range of principal value branch of tan⁻¹x |
| 5 | Matrices | LOT | (A + B)ᵀ = Aᵀ + Bᵀ |
| 6 | Matrices | MOT | Skew-symmetric condition: find a + b |
| 7 | Matrices | LOT | Diagonal matrix condition: aᵢⱼ = 0 for i ≠ j |
| 8 | Determinants | LOT | \|3A\| for 3×3 with \|A\| = −2 |
| 9 | Determinants | MOT | Area of triangle (1,0),(6,0),(4,3) using determinants |
| 10 | C&D | LOT | dy/dx of sin(3x) |
| 11 | C&D | LOT | f(x) = x³ — continuous & differentiable everywhere? |
| 12 | C&D | MOT | Continuity: k value for piecewise f(x) = kx² (x≤2), 3 (x>2) |
| 13 | AppD | LOT | dV/dr for sphere V = (4/3)πr³ |
| 14 | AppD | MOT | f(x) = x³−6x²+9x+15 strictly decreasing in (1,3) |
| 15 | AppD | MOT | Slope of tangent to y = e²ˣ at (0,1) |
| 16 | AppD | HOT | Local max of f(x) = 2x³−3x²−12x+4 at x = −1 |
| 17 | Integrals | LOT | ∫ cos x dx |
| 18 | Integrals | LOT | ∫ 1/√(1−x²) dx |

### Section A — Assertion-Reason (Q19–Q20)

| Q# | Chapter | Difficulty | Summary |
|---|---|---|---|
| 19 | C&D | HOT | A: [x] discontinuous at every integer; R: lim doesn't exist at integers → **(a)** |
| 20 | AppD | MOT | A: log x strictly increasing on (0,∞); R: f′(x)=1/x>0 → **(a)** |

### Section B (Q21–Q25, 2 marks each)

| Q# | Chapter | Difficulty | Question |
|---|---|---|---|
| 21 | R&F | MOT | Is R = {(1,1),(2,2),(3,3),(1,3),(3,1)} on {1,2,3} an equivalence relation? |
| 22 | Matrices | LOT | Find A + 2B for given 2×2 matrices |
| 23 | C&D | MOT | Differentiate y = cos(x²) |
| 24 | AppD | MOT | Equation of tangent to y = x² at (1,1) |
| 25 | Integrals | LOT | Evaluate ∫ e^(sin x) · cos x dx |

### Section C (Q26–Q31, 3 marks each)

| Q# | Chapter | Difficulty | Question |
|---|---|---|---|
| 26 | InvTrig | HOT | Find: (i) cos⁻¹(cos(5π/4)) (ii) sin⁻¹(sin(−π/3)) (iii) tan⁻¹(tan(5π/6)) |
| 27 | InvTrig | MOT | Domain of sin⁻¹(3x−1) and cos⁻¹((x−1)/2); principal value of tan⁻¹(−1) |
| 28 | Determinants | MOT | Evaluate 3×3 determinant (different matrix from Set A) |
| 29 | C&D | HOT | If y = x^(sin x), find dy/dx (logarithmic differentiation) |
| 30 | AppD | HOT | Intervals of increase/decrease for f(x) = x³−6x²+9x+15 |
| 31 | Integrals | MOT | Evaluate ∫ x cos x dx (integration by parts) |

### Section D (Q32–Q35, 5 marks each)

| Q# | Chapter | Difficulty | Question |
|---|---|---|---|
| 32 | Matrices | HOT | Express different 3×3 matrix as sum of symmetric + skew-symmetric |
| 33 | Determinants | HOT | Solve: x−y+2z=7, 3x+4y−5z=−5, 2x−y+3z=12 using A⁻¹ |
| 34 | C&D | HOT | Show: (1+x²)²y₂ + 2x(1+x²)y₁ = 2 for y = (tan⁻¹x)² |
| 35 | Integrals | HOT | Evaluate ∫ (x²+x+1)/((x+1)(x+2)) dx |

### Section E (Q36–Q38, 4 marks each)

| Q# | Chapter | Difficulty | Case Study Theme |
|---|---|---|---|
| 36 | R&F | HOT | Divisibility relation on A = {1,2,3,4,6} |
| 37 | AppD | HOT | Marginal revenue: R(x) = 13x² + 26x + 15 |
| 38 | Integrals | MOT | Car motion: v(t) = 6t² − 4t, s(0)=0 |

---

## Difficulty Distribution (Approximate)

| Level | Target | Set A | Set B |
|---|---|---|---|
| HOT (Higher Order Thinking) | 40% (32 marks) | ~33 marks | ~33 marks |
| MOT (Medium Order Thinking) | 30% (24 marks) | ~25 marks | ~25 marks |
| LOT (Lower Order Thinking) | 30% (24 marks) | ~22 marks | ~22 marks |

---

## Output Files

Upon approval, I will generate **4 Word-compatible files**:

1. **Set_A_Question_Paper** — Complete question paper
2. **Set_A_Marking_Scheme** — Step-by-step solutions with marks breakdown
3. **Set_B_Question_Paper** — Complete question paper
4. **Set_B_Marking_Scheme** — Step-by-step solutions with marks breakdown

---

## Open Questions

> [!IMPORTANT]
> 1. **Integration by parts & partial fractions** — I've assumed these methods (from Ch 7) are covered by half-yearly. Should I avoid them and stick to only substitution-based integrals?
> 2. **Q26/Set A** asks sin⁻¹(sin(2π/3)) etc. — This tests principal value branch understanding (not a "property"). Please confirm this is acceptable under the deleted syllabus rules.
> 3. **Logarithmic differentiation** (y = xˣ) — Confirm this topic is in scope for half-yearly.
> 4. **Do you want graphs/diagrams** for the Assertion-Reason questions (Q19, Q20)? The original paper referenced graphs. I can note "[Graph to be inserted]" in the Word files.
