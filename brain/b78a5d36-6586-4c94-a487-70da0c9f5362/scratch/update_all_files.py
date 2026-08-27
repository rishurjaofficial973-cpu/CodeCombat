"""
Update text files and build all Word documents with embedded images.
"""

import os
from docx import Document
from docx.shared import Pt, Cm, Inches, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.section import WD_ORIENT

BASE_DIR = r"C:\Users\rajan\.gemini\antigravity\scratch\math_exam_2026"
IMG_DIR = os.path.join(BASE_DIR, "images")


def write_text_files():
    # Set A Question Paper text
    with open(os.path.join(BASE_DIR, "Set_A_Question_Paper.txt"), 'w', encoding='utf-8') as f:
        f.write("""HALF-YEARLY EXAMINATION 2026–27
CLASS XII — MATHEMATICS (SET A)
Time Allowed: 3 Hours                                          Maximum Marks: 80
--------------------------------------------------------------------------------
General Instructions:
1. This question paper contains five sections — A, B, C, D and E. All questions are compulsory.
2. Section A consists of 20 questions of 1 mark each (Q1–Q20).
3. Question numbers 19 and 20 are Assertion–Reason based questions. Two statements are given, marked Assertion (A) and Reason (R). Select the correct option from the four given below each question.
4. Section B consists of 5 questions of 2 marks each (Q21–Q25).
5. Section C consists of 6 questions of 3 marks each (Q26–Q31).
6. Section D consists of 4 questions of 5 marks each (Q32–Q35).
7. Section E consists of 3 case-based/source-based questions of 4 marks each (Q36–Q38), with sub-parts of 2, 1 and 1 marks.
8. There is no overall choice. All questions are compulsory as set.
9. Use of calculators is not permitted.
--------------------------------------------------------------------------------

                                  SECTION A
(Question numbers 1 to 18 carry 1 mark each. Questions 19 and 20 are Assertion–Reason based, 1 mark each.)

1. Let A = {1, 2, 3} and R = {(1,1), (2,2), (3,3), (1,2)} be a relation on A. Then R is:
(a) An equivalence relation
(b) Reflexive and transitive only
(c) Reflexive and symmetric only
(d) Symmetric and transitive only

2. The function f : ℝ → ℝ defined by f(x) = x² is:
(a) One-one and onto
(b) One-one but not onto
(c) Onto but not one-one
(d) Neither one-one nor onto

3. The principal value of sin⁻¹(−1/2) is:
(a) π/6            (b) −π/6           (c) 5π/6           (d) −5π/6

4. The domain of cos⁻¹(2x − 1) is:
(a) [0, 1]         (b) [−1, 1]        (c) [−1, 0]        (d) [0, 2]

5. For any square matrix A, the matrix (A + Aᵀ) is always:
(a) Skew-symmetric                     (b) Symmetric
(c) A diagonal matrix                  (d) A null matrix

6. The value of x for which the matrix A = [[0, 1, -2], [-1, 0, x], [2, -3, 0]] is skew-symmetric, is:
(a) 3              (b) −3             (c) 2              (d) −2

7. If A is any square matrix, then the matrix (A − Aᵀ) is always:
(a) Symmetric                          (b) Skew-symmetric
(c) Null matrix                        (d) Identity matrix

8. If A is a 3 × 3 matrix such that |A| = 5, then the value of |3A| is:
(a) 15             (b) 45             (c) 135            (d) 405

9. If A is a square matrix of order 3 with |A| = 4, then |adj A| equals:
(a) 4              (b) 8              (c) 16             (d) 64

10. The function f(x) = |x| is:
(a) Continuous and differentiable everywhere
(b) Continuous everywhere but not differentiable at x = 0
(c) Discontinuous at x = 0
(d) Differentiable everywhere but not continuous

11. If y = e²ˣ, then dy/dx equals:
(a) e²ˣ            (b) 2e²ˣ           (c) 2x e²ˣ         (d) eˣ

12. If f(x) = kx + 1 for x ≤ 5 and f(x) = 3x − 5 for x > 5 is continuous at x = 5, then k =:
(a) 9/5            (b) 5/9            (c) 2              (d) 3

13. The rate of change of the area of a circle with respect to its radius r, at r = 5 cm, is:
(a) 5π cm²/cm      (b) 10π cm²/cm     (c) 25π cm²/cm     (d) 2π cm²/cm

14. The function f(x) = x² − 4x + 6 is strictly increasing in the interval:
(a) (−∞, 2)        (b) (2, ∞)         (c) (−2, 2)        (d) ℝ

15. The maximum value of the function f(x) = sin x + cos x on the interval [0, π/2] is:
(a) 1              (b) √2             (c) 2              (d) 1/√2

16. The total revenue (in ₹) from the sale of x units of a product is R(x) = 3x² + 36x + 5. The marginal revenue when x = 15 is:
(a) ₹ 116          (b) ₹ 96           (c) ₹ 90           (d) ₹ 126

17. ∫ sec²x dx equals:
(a) tan x + C      (b) −cot x + C     (c) sec x tan x + C (d) cot x + C

18. ∫ 1/(1 + x²) dx equals:
(a) tan⁻¹x + C     (b) sin⁻¹x + C     (c) cot⁻¹x + C     (d) sec⁻¹x + C

19. The graph of a function y = f(x) is shown in Figure 19.
[Figure 19: Graph of f(x) = |x - 2|]
Assertion (A): The function f(x) = |x − 2| shown in the graph is continuous at x = 2.
Reason (R): The function f(x) = |x − 2| is differentiable at x = 2.
(a) Both A and R are true, and R is the correct explanation of A.
(b) Both A and R are true, but R is not the correct explanation of A.
(c) A is true, but R is false.
(d) A is false, but R is true.

20. The graph of a function y = f(x) is shown in Figure 20.
[Figure 20: Graph of f(x) = -(x - 3)² + 4]
Assertion (A): The point (3, 4) marked on the graph of f(x) = −(x − 3)² + 4 is a point of local maximum.
Reason (R): At a point of local maximum, f′(x) = 0 and f″(x) < 0.
(a) Both A and R are true, and R is the correct explanation of A.
(b) Both A and R are true, but R is not the correct explanation of A.
(c) A is true, but R is false.
(d) A is false, but R is true.

                                  SECTION B
(Question numbers 21 to 25 carry 2 marks each.)

21. Check whether the relation R = {(1,1), (2,2), (3,3), (1,2), (2,3)} on the set A = {1, 2, 3} is reflexive, symmetric and transitive. Justify your answer.

22. If A = [[1, 2], [3, 4]] and B = [[2, 0], [1, 3]], find 2A − 3B.

23. Differentiate y = sin(x² + 1) with respect to x.

24. An edge of a variable cube is increasing at the rate of 3 cm/s. How fast is the volume of the cube increasing when the edge is 10 cm long?

25. Evaluate: ∫ x e^(x²) dx.

                                  SECTION C
(Question numbers 26 to 31 carry 3 marks each.)

26. Find the value of:
(i) sin⁻¹(sin(2π/3))
(ii) cos⁻¹(cos(7π/6))
(iii) tan⁻¹(tan(3π/4))

27. Find the value of: tan⁻¹(1) + cos⁻¹(−1/2) + sin⁻¹(−1/2).

28. Using minors and cofactors, evaluate the determinant:
    | 1   2   3 |
    | 0   1   4 |
    | 5   6   0 |

29. If y = xˣ (x > 0), find dy/dx.

30. Find the intervals in which the function f(x) = 2x³ − 15x² + 36x + 1 is (i) strictly increasing (ii) strictly decreasing.

31. Evaluate ∫ x sin x dx using integration by parts.

                                  SECTION D
(Question numbers 32 to 35 carry 5 marks each.)

32. Express the matrix A = [[1, 2, -3], [3, -1, 2], [-2, 1, 4]] as the sum of a symmetric and a skew-symmetric matrix.

33. Using the matrix method, solve the following system of linear equations:
    x + y + z = 6
    y + 3z = 11
    x − 2y + z = 0

34. If y = (sin⁻¹x)², prove that (1 − x²) d²y/dx² − x dy/dx − 2 = 0.

35. Evaluate: ∫ (x² + 1)/(x² − 5x + 6) dx.

                                  SECTION E
(Question numbers 36 to 38 are case-based questions of 4 marks each, with sub-parts of 2, 1 and 1 marks.)

36. Case Study 1 — Relations and Functions
Two friends, Rohan and Simran, are studying equivalence relations on sets. Consider the set A = {1, 2, 3, 4, 5} and the relation R on A defined by R = {(a, b) : |a − b| is even}.
[Figure 36: Partition of Set A into Equivalence Classes]
Based on the above information, answer the following:
(i) Show that R is an equivalence relation on A.                             (2 marks)
(ii) Write the equivalence class of the element 2.                          (1 mark)
(iii) Is (1, 5) ∈ R? Justify your answer.                                  (1 mark)

37. Case Study 2 — Application of Derivatives
A manufacturing firm analyzes its production costs. The total cost of producing x units of a product is modelled by the cost function C(x) = 0.005x³ − 0.02x² + 30x + 5000 (in ₹).
[Figure 37: Production Cost Function Curve]
Based on the above information, answer the following:
(i) Find the marginal cost function, MC(x) = dC/dx.                         (2 marks)
(ii) Find the marginal cost when x = 3 units.                               (1 mark)
(iii) State, with reason, whether the cost is increasing or decreasing at x = 3. (1 mark)

38. Case Study 3 — Integrals
A particle moves along a straight line such that its velocity at time t seconds is given by v(t) = 3t² − 12t + 9 (in m/s). It is given that the particle starts at the origin, i.e. s(0) = 0.
[Figure 38: Velocity vs Time Graph]
Based on the above information, answer the following:
(i) Find the displacement function s(t).                                    (2 marks)
(ii) Find the displacement of the particle at t = 2 s.                       (1 mark)
(iii) Find the value(s) of t ∈ [0, 3] at which the particle is momentarily at rest. (1 mark)

— End of Question Paper —
""")

    # Set A Marking Scheme text
    with open(os.path.join(BASE_DIR, "Set_A_Marking_Scheme.txt"), 'w', encoding='utf-8') as f:
        f.write("""HALF-YEARLY EXAMINATION 2026–27
CLASS XII — MATHEMATICS (SET A)
MARKING SCHEME & STEP-BY-STEP SOLUTIONS
Time Allowed: 3 Hours                                          Maximum Marks: 80
--------------------------------------------------------------------------------
GENERAL GUIDELINES FOR EVALUATORS:
1. Marks should be awarded for every correct step as indicated in the marking scheme.
2. Full credit should be given for alternative correct methods.
3. For Section A MCQs, 1 mark is awarded for the correct option along with reasoning.
--------------------------------------------------------------------------------

SECTION A (20 Marks — 1 Mark Each)

Q1. (b) Reflexive and transitive only                                        [1 mark]
    Reason: (1,1),(2,2),(3,3) in R (reflexive). (1,2) in R but (2,1) not in R (not symmetric). No transitive violation exists.

Q2. (d) Neither one-one nor onto                                            [1 mark]
    Reason: f(1)=f(-1)=1 (not 1-1). Range = [0, inf) != R (codomain) (not onto).

Q3. (b) −π/6                                                                 [1 mark]
    Reason: sin(-pi/6) = -1/2 and -pi/6 in [-pi/2, pi/2].

Q4. (a) [0, 1]                                                               [1 mark]
    Reason: -1 <= 2x - 1 <= 1 ==> 0 <= 2x <= 2 ==> 0 <= x <= 1.

Q5. (b) Symmetric                                                           [1 mark]
    Reason: (A + A^T)^T = A^T + A = A + A^T.

Q6. (a) 3                                                                    [1 mark]
    Reason: a_23 = -a_32 ==> x = -(-3) = 3.

Q7. (b) Skew-symmetric                                                      [1 mark]
    Reason: (A - A^T)^T = A^T - A = -(A - A^T).

Q8. (c) 135                                                                  [1 mark]
    Reason: |3A| = 3^3 |A| = 27 * 5 = 135.

Q9. (c) 16                                                                   [1 mark]
    Reason: |adj A| = |A|^(n-1) = 4^(3-1) = 16.

Q10. (b) Continuous everywhere but not differentiable at x = 0               [1 mark]
    Reason: LHD at 0 is -1, RHD at 0 is +1.

Q11. (b) 2e²ˣ                                                                [1 mark]
    Reason: d/dx(e^(2x)) = 2e^(2x).

Q12. (a) 9/5                                                                 [1 mark]
    Reason: 5k + 1 = 3(5) - 5 = 10 ==> k = 9/5.

Q13. (b) 10π cm²/cm                                                          [1 mark]
    Reason: dA/dr = 2*pi*r = 10*pi at r = 5.

Q14. (b) (2, ∞)                                                              [1 mark]
    Reason: f'(x) = 2x - 4 > 0 ==> x > 2.

Q15. (b) √2                                                                  [1 mark]
    Reason: f'(x) = cos x - sin x = 0 ==> x = pi/4. f(pi/4) = 1/sqrt(2) + 1/sqrt(2) = sqrt(2). At endpoints: f(0)=1, f(pi/2)=1. Max value is sqrt(2).

Q16. (d) ₹ 126                                                               [1 mark]
    Reason: MR = dR/dx = 6x + 36. At x = 15: MR = 6(15) + 36 = 90 + 36 = 126.

Q17. (a) tan x + C                                                           [1 mark]
    Reason: Standard formula.

Q18. (a) tan⁻¹x + C                                                          [1 mark]
    Reason: Standard formula.

Q19. (c) A is true, but R is false                                          [1 mark]
    Reason: f(x) = |x - 2| is continuous everywhere, but not differentiable at the corner x = 2.

Q20. (a) Both A and R are true, and R is the correct explanation of A        [1 mark]
    Reason: f'(3) = -2(0) = 0 and f''(3) = -2 < 0 confirms local maximum at (3, 4).

--------------------------------------------------------------------------------
SECTION B (5 × 2 = 10 Marks)

Q21. Check Reflexive, Symmetric, Transitive for R = {(1,1),(2,2),(3,3),(1,2),(2,3)}:
- Reflexive: (1,1),(2,2),(3,3) in R ==> Reflexive.                          [1/2 mark]
- Symmetric: (1,2) in R but (2,1) not in R ==> Not symmetric.               [1/2 mark]
- Transitive: (1,2) in R and (2,3) in R but (1,3) not in R ==> Not transitive. [1 mark]
Final Answer: R is reflexive, but neither symmetric nor transitive.

Q22. 2A - 3B:
2A = [[2, 4], [6, 8]]                                                        [1/2 mark]
3B = [[6, 0], [3, 9]]                                                        [1/2 mark]
2A - 3B = [[2-6, 4-0], [6-3, 8-9]] = [[-4, 4], [3, -1]]                     [1 mark]
Final Answer: [[-4, 4], [3, -1]]

Q23. Differentiate y = sin(x² + 1):
dy/dx = cos(x² + 1) * d/dx(x² + 1)                                           [1 mark]
      = 2x cos(x² + 1)                                                       [1 mark]
Final Answer: 2x cos(x² + 1)

Q24. Rate of increase of cube volume:
Let x be edge length, V = x^3.
dV/dt = 3x^2 * (dx/dt)                                                       [1 mark]
Given dx/dt = 3 cm/s and x = 10 cm:
dV/dt = 3(10)^2 * (3) = 3 * 100 * 3 = 900 cm^3/s.                           [1 mark]
Final Answer: 900 cm³/s

Q25. Evaluate ∫ x e^(x²) dx:
Let u = x^2 ==> du = 2x dx ==> x dx = du/2                                  [1 mark]
∫ x e^(x²) dx = (1/2) ∫ e^u du = (1/2) e^u + C = (1/2) e^(x²) + C            [1 mark]
Final Answer: (1/2) e^(x²) + C

--------------------------------------------------------------------------------
SECTION C (6 × 3 = 18 Marks)

Q26. Principal value evaluations:
(i) sin⁻¹(sin(2π/3)) = sin⁻¹(sin(π - π/3)) = sin⁻¹(sin(π/3)) = π/3          [1 mark]
(ii) cos⁻¹(cos(7π/6)) = cos⁻¹(cos(2π - 5π/6)) = cos⁻¹(cos(5π/6)) = 5π/6      [1 mark]
(iii) tan⁻¹(tan(3π/4)) = tan⁻¹(tan(π - π/4)) = tan⁻¹(-tan(π/4)) = -π/4       [1 mark]

Q27. tan⁻¹(1) + cos⁻¹(−1/2) + sin⁻¹(−1/2):
tan⁻¹(1) = π/4                                                               [1 mark]
cos⁻¹(−1/2) = π - π/3 = 2π/3                                                 [1/2 mark]
sin⁻¹(−1/2) = −π/6                                                           [1/2 mark]
Sum = π/4 + 2π/3 − π/6 = (3π + 8π − 2π)/12 = 9π/12 = 3π/4                  [1 mark]
Final Answer: 3π/4

Q28. Determinant by minors/cofactors along R1:
|A| = 1 * C_11 + 2 * C_12 + 3 * C_13                                         [1 mark]
C_11 = +(1*0 - 4*6) = -24
C_12 = -(0*0 - 4*5) = +20
C_13 = +(0*6 - 1*5) = -5                                                     [1 mark]
|A| = 1(-24) + 2(20) + 3(-5) = -24 + 40 - 15 = 1                            [1 mark]
Final Answer: 1

Q29. y = xˣ (x > 0):
ln y = x ln x                                                                [1/2 mark]
(1/y) dy/dx = x * (1/x) + ln x * 1 = 1 + ln x                                [1.5 marks]
dy/dx = y(1 + ln x) = xˣ(1 + ln x)                                           [1 mark]
Final Answer: xˣ(1 + ln x)

Q30. Intervals of increase/decrease for f(x) = 2x³ − 15x² + 36x + 1:
f'(x) = 6x² − 30x + 36 = 6(x² − 5x + 6) = 6(x − 2)(x − 3)                   [1 mark]
Setting f'(x) = 0 gives critical points x = 2, x = 3.
- In (-inf, 2): f'(x) > 0 ==> Strictly Increasing                            [1 mark]
- In (2, 3): f'(x) < 0 ==> Strictly Decreasing                               [1/2 mark]
- In (3, inf): f'(x) > 0 ==> Strictly Increasing                             [1/2 mark]
Final Answer: (i) Strictly increasing on (-∞, 2) ∪ (3, ∞); (ii) Strictly decreasing on (2, 3)

Q31. Evaluate ∫ x sin x dx:
Using integration by parts: ∫ u v' dx = u v - ∫ u' v dx
Let u = x ==> u' = 1; v' = sin x ==> v = -cos x                              [1 mark]
∫ x sin x dx = -x cos x - ∫ 1 * (-cos x) dx                                  [1 mark]
             = -x cos x + ∫ cos x dx = -x cos x + sin x + C                  [1 mark]
Final Answer: -x cos x + sin x + C

--------------------------------------------------------------------------------
SECTION D (4 × 5 = 20 Marks)

Q32. Express A = [[1, 2, -3], [3, -1, 2], [-2, 1, 4]] as P + Q (Symmetric + Skew-symmetric):
A^T = [[1, 3, -2], [2, -1, 1], [-3, 2, 4]]                                   [1 mark]
P = (A + A^T)/2 = [[1, 5/2, -5/2], [5/2, -1, 3/2], [-5/2, 3/2, 4]]          [1.5 marks]
Q = (A - A^T)/2 = [[0, -1/2, -1/2], [1/2, 0, 1/2], [1/2, -1/2, 0]]          [1.5 marks]
Verification: P^T = P (symmetric), Q^T = -Q (skew-symmetric), and P + Q = A. [1 mark]

Q33. Solve system using matrix method:
AX = B where A = [[1,1,1],[0,1,3],[1,-2,1]], X = [[x],[y],[z]], B = [[6],[11],[0]]
|A| = 1(1 - (-6)) - 1(0 - 3) + 1(0 - 1) = 7 + 3 - 1 = 9 != 0                 [1 mark]
Cofactors of A:
C_11 = 7, C_12 = 3, C_13 = -1
C_21 = -3, C_22 = 0, C_23 = 3
C_31 = 2, C_32 = -3, C_33 = 1                                                [1.5 marks]
adj A = [[7, -3, 2], [3, 0, -3], [-1, 3, 1]]                                [1/2 mark]
A^(-1) = (1/9) adj A                                                         [1/2 mark]
X = A^(-1) B = (1/9) [[7,-3,2],[3,0,-3],[-1,3,1]] [[6],[11],[0]]
  = (1/9) [[42 - 33 + 0], [18 + 0 + 0], [-6 + 33 + 0]]
  = (1/9) [[9], [18], [27]] = [[1], [2], [3]]                                [1.5 marks]
Final Answer: x = 1, y = 2, z = 3

Q34. y = (sin⁻¹x)², prove (1 - x²) y₂ - x y₁ - 2 = 0:
dy/dx = y₁ = 2 sin⁻¹x * (1/√(1 - x²))                                        [1 mark]
√(1 - x²) * y₁ = 2 sin⁻¹x                                                    [1 mark]
Differentiating both sides with respect to x:
√(1 - x²) * y₂ + y₁ * (-2x / 2√(1 - x²)) = 2 / √(1 - x²)                     [1.5 marks]
√(1 - x²) * y₂ - (x y₁)/√(1 - x²) = 2 / √(1 - x²)
Multiplying by √(1 - x²):
(1 - x²) y₂ - x y₁ = 2                                                       [1 mark]
==> (1 - x²) y₂ - x y₁ - 2 = 0. Hence Proved.                               [1/2 mark]

Q35. Evaluate ∫ (x² + 1)/(x² − 5x + 6) dx:
By polynomial division:
(x² + 1)/(x² − 5x + 6) = 1 + (5x − 5)/(x² − 5x + 6)                         [1 mark]
Partial fractions: (5x - 5)/((x - 2)(x - 3)) = A/(x - 2) + B/(x - 3)         [1/2 mark]
5x - 5 = A(x - 3) + B(x - 2)
At x = 2: 5 = -A ==> A = -5
At x = 3: 10 = B ==> B = 10                                                  [1.5 marks]
∫ [1 - 5/(x - 2) + 10/(x - 3)] dx = x - 5 ln|x - 2| + 10 ln|x - 3| + C       [2 marks]
Final Answer: x - 5 ln|x - 2| + 10 ln|x - 3| + C

--------------------------------------------------------------------------------
SECTION E (3 × 4 = 12 Marks)

Q36. Case Study 1 — Relations and Functions:
(i) Show R is an equivalence relation:
- Reflexive: For all a in A, |a - a| = 0 (even) ==> (a, a) in R.             [1/2 mark]
- Symmetric: (a, b) in R ==> |a - b| is even ==> |b - a| is even ==> (b, a) in R. [1/2 mark]
- Transitive: (a, b) in R and (b, c) in R ==> a - b = 2k_1, b - c = 2k_2 ==> a - c = 2(k_1 + k_2) ==> |a - c| is even ==> (a, c) in R. [1 mark]
Hence R is an equivalence relation.

(ii) Equivalence class of 2:
[2] = {b in A : |2 - b| is even} = {2, 4}.                                   [1 mark]

(iii) Is (1, 5) in R?
|1 - 5| = 4, which is an even integer. Yes, (1, 5) in R.                     [1 mark]

Q37. Case Study 2 — Application of Derivatives:
(i) MC(x) = dC/dx = 0.005(3x²) − 0.02(2x) + 30
          = 0.015x² − 0.04x + 30                                             [2 marks]

(ii) Marginal cost when x = 3:
MC(3) = 0.015(3²) − 0.04(3) + 30 = 0.135 − 0.12 + 30 = ₹ 30.015             [1 mark]

(iii) Cost increasing/decreasing at x = 3:
Since MC(3) = 30.015 > 0, the cost function C(x) is increasing at x = 3.     [1 mark]

Q38. Case Study 3 — Integrals:
(i) Displacement s(t):
s(t) = ∫ (3t² − 12t + 9) dt = t³ − 6t² + 9t + C                              [1 mark]
Given s(0) = 0 ==> C = 0. Thus, s(t) = t³ − 6t² + 9t.                        [1 mark]

(ii) Displacement at t = 2 s:
s(2) = (2)³ − 6(2)² + 9(2) = 8 − 24 + 18 = 2 m.                             [1 mark]

(iii) Momentarily at rest:
v(t) = 0 ==> 3t² − 12t + 9 = 0 ==> 3(t − 1)(t − 3) = 0 ==> t = 1 s, t = 3 s. [1 mark]
Both values lie in [0, 3].

— End of Marking Scheme —
""")

    print("Set A text files written.")


def write_set_b_text_files():
    with open(os.path.join(BASE_DIR, "Set_B_Question_Paper.txt"), 'w', encoding='utf-8') as f:
        f.write("""HALF-YEARLY EXAMINATION 2026–27
CLASS XII — MATHEMATICS (SET B)
Time Allowed: 3 Hours                                          Maximum Marks: 80
--------------------------------------------------------------------------------
General Instructions:
1. This question paper contains five sections — A, B, C, D and E. All questions are compulsory.
2. Section A consists of 20 questions of 1 mark each (Q1–Q20).
3. Question numbers 19 and 20 are Assertion–Reason based questions. Two statements are given, marked Assertion (A) and Reason (R). Select the correct option from the four given below each question.
4. Section B consists of 5 questions of 2 marks each (Q21–Q25).
5. Section C consists of 6 questions of 3 marks each (Q26–Q31).
6. Section D consists of 4 questions of 5 marks each (Q32–Q35).
7. Section E consists of 3 case-based/source-based questions of 4 marks each (Q36–Q38), with sub-parts of 2, 1 and 1 marks.
8. There is no overall choice. All questions are compulsory as set.
9. Use of calculators is not permitted.
--------------------------------------------------------------------------------

                                  SECTION A
(Question numbers 1 to 18 carry 1 mark each. Questions 19 and 20 are Assertion–Reason based, 1 mark each.)

1. Let R = {(1,1), (2,2), (3,3), (1,3), (3,1)} be a relation on A = {1, 2, 3}. Then R is:
(a) Reflexive only
(b) Symmetric only
(c) An equivalence relation
(d) Reflexive and symmetric only

2. The number of all one-one functions from the set {1, 2, 3} to itself is:
(a) 3              (b) 6              (c) 9              (d) 27

3. The principal value of cos⁻¹(−1/√2) is:
(a) π/4            (b) 3π/4           (c) −π/4           (d) 5π/4

4. The range of the principal value branch of tan⁻¹x is:
(a) [−π/2, π/2]    (b) (−π/2, π/2)    (c) [0, π]         (d) (0, π)

5. If A and B are square matrices of the same order, then (A + B)ᵀ equals:
(a) Aᵀ + Bᵀ        (b) Aᵀ − Bᵀ        (c) AᵀBᵀ           (d) BᵀAᵀ

6. If the matrix A = [[0, a, -3], [2, 0, -1], [b, 1, 0]] is skew-symmetric, then the value of (a + b) is:
(a) 1              (b) −1             (c) 5              (d) −5

7. A matrix A = [aᵢⱼ]₃ₓ₃ is a diagonal matrix if:
(a) aᵢⱼ = 0 for all i, j               (b) aᵢⱼ = 0 for i ≠ j
(c) aᵢⱼ = 0 for i = j                  (d) aᵢⱼ ≠ 0 for all i, j

8. If A is a 3 × 3 matrix with |A| = −2, then |3A| equals:
(a) −6             (b) −54            (c) 54             (d) −18

9. The area of the triangle with vertices (1, 0), (6, 0) and (4, 3), found using determinants, is:
(a) 15 sq units    (b) 15/2 sq units  (c) 7 sq units     (d) 12 sq units

10. If y = sin(3x), then dy/dx is:
(a) cos(3x)        (b) 3cos(3x)       (c) −3cos(3x)      (d) 3sin(3x)

11. The function f(x) = x³ is:
(a) Continuous but not differentiable at x = 0
(b) Differentiable but not continuous at x = 0
(c) Both continuous and differentiable everywhere on ℝ
(d) Neither continuous nor differentiable at x = 0

12. The value of k for which f(x) = kx² (for x ≤ 2) and f(x) = 3 (for x > 2) is continuous at x = 2, is:
(a) 3/4            (b) 4/3            (c) 3              (d) 4

13. The rate of change of the volume V = (4/3)πr³ of a sphere with respect to its radius r is:
(a) 4πr²           (b) 2πr            (c) (4/3)πr²       (d) 4πr

14. The function f(x) = x³ − 6x² + 9x + 15 is strictly decreasing in the interval:
(a) (−∞, 1)        (b) (1, 3)         (c) (3, ∞)         (d) (1, ∞)

15. The rate of change of the total surface area S = 4πr² of a sphere with respect to its radius r, when r = 3 cm, is:
(a) 12π cm²/cm     (b) 24π cm²/cm     (c) 36π cm²/cm     (d) 6π cm²/cm

16. The function f(x) = 2x³ − 3x² − 12x + 4 has a local maximum at:
(a) x = 2          (b) x = −1         (c) x = 0          (d) x = 1

17. ∫ cos x dx equals:
(a) sin x + C      (b) −sin x + C     (c) cos x + C      (d) −cos x + C

18. ∫ 1/√(1 − x²) dx equals:
(a) sin⁻¹x + C     (b) cos⁻¹x + C     (c) tan⁻¹x + C     (d) sec⁻¹x + C

19. The graph of the greatest integer function y = [x] is shown in Figure 19.
[Figure 19: Step Discontinuities of Greatest Integer Function]
Assertion (A): The function f(x) = [x] (greatest integer function) is discontinuous at every integer.
Reason (R): For any integer n, lim(x→n) [x] does not exist.
(a) Both A and R are true, and R is the correct explanation of A.
(b) Both A and R are true, but R is not the correct explanation of A.
(c) A is true, but R is false.
(d) A is false, but R is true.

20. The graph of the logarithmic function y = ln(x) is shown in Figure 20.
[Figure 20: Graph of f(x) = ln(x)]
Assertion (A): The function f(x) = log x is strictly increasing on (0, ∞).
Reason (R): f′(x) = 1/x > 0 for all x ∈ (0, ∞).
(a) Both A and R are true, and R is the correct explanation of A.
(b) Both A and R are true, but R is not the correct explanation of A.
(c) A is true, but R is false.
(d) A is false, but R is true.

                                  SECTION B
(Question numbers 21 to 25 carry 2 marks each.)

21. Let A = {1, 2, 3} and R = {(1,1), (2,2), (3,3), (1,3), (3,1)} be a relation on A. Is R an equivalence relation? Justify your answer.

22. If A = [[3, 1], [-1, 2]] and B = [[1, 0], [-1, 2]], find A + 2B.

23. Differentiate y = cos(x²) with respect to x.

24. The radius of a circle is increasing uniformly at the rate of 3 cm/s. Find the rate at which the area of the circle is increasing when the radius is 10 cm.

25. Evaluate: ∫ e^(sin x) · cos x dx.

                                  SECTION C
(Question numbers 26 to 31 carry 3 marks each.)

26. Find the value of:
(i) cos⁻¹(cos(5π/4))
(ii) sin⁻¹(sin(−π/3))
(iii) tan⁻¹(tan(5π/6))

27. Find the domain of:
(i) f(x) = sin⁻¹(3x − 1)
(ii) g(x) = cos⁻¹((x − 1)/2)
Also, find the principal value of tan⁻¹(−1).

28. Evaluate the determinant:
    |  2  -1   3 |
    |  1   2  -1 |
    |  0   3   2 |

29. If y = x^(sin x) (x > 0), find dy/dx.

30. Find the intervals in which the function f(x) = x³ − 6x² + 9x + 15 is (i) strictly increasing (ii) strictly decreasing.

31. Evaluate ∫ x cos x dx using integration by parts.

                                  SECTION D
(Question numbers 32 to 35 carry 5 marks each.)

32. Express the matrix A = [[3, -2, 5], [4, 1, -3], [-1, 2, 1]] as the sum of a symmetric and a skew-symmetric matrix.

33. Using the matrix method, solve the following system of linear equations:
    x − y + 2z = 7
    3x + 4y − 5z = −5
    2x − y + 3z = 12

34. If y = (tan⁻¹x)², show that (1 + x²)² d²y/dx² + 2x(1 + x²) dy/dx = 2.

35. Evaluate: ∫ (x² + x + 1)/((x + 1)(x + 2)) dx.

                                  SECTION E
(Question numbers 36 to 38 are case-based questions of 4 marks each, with sub-parts of 2, 1 and 1 marks.)

36. Case Study 1 — Relations and Functions
A mathematics club defines a relation R on the set A = {1, 2, 3, 4, 6} as R = {(a, b) : a divides b, where a, b ∈ A}.
[Figure 36: Divisibility Relation Graph on Set A]
Based on the above information, answer the following:
(i) Show that R is reflexive and transitive.                                 (2 marks)
(ii) Is R symmetric? Justify with an example.                                (1 mark)
(iii) Is R an equivalence relation? Give reason.                             (1 mark)

37. Case Study 2 — Application of Derivatives
A manufacturer's total revenue (in ₹) from the sale of x units of a commodity is given by R(x) = 13x² + 26x + 15.
[Figure 37: Total Revenue Curve]
Based on the above information, answer the following:
(i) Find the marginal revenue function, MR(x) = dR/dx.                       (2 marks)
(ii) Find the marginal revenue when x = 7 units.                             (1 mark)
(iii) State, with reason, whether the revenue is increasing or decreasing at x = 7. (1 mark)

38. Case Study 3 — Integrals
A car starts from rest and its velocity at time t seconds is given by v(t) = 6t² − 4t (in m/s). It is given that the displacement at t = 0 is zero, i.e. s(0) = 0.
[Figure 38: Velocity vs Time Graph]
Based on the above information, answer the following:
(i) Find the displacement function s(t).                                     (2 marks)
(ii) Find the displacement at t = 3 s.                                       (1 mark)
(iii) Find the value of t (t > 0) at which the car is momentarily at rest.   (1 mark)

— End of Question Paper —
""")

    # Set B Marking Scheme text
    with open(os.path.join(BASE_DIR, "Set_B_Marking_Scheme.txt"), 'w', encoding='utf-8') as f:
        f.write("""HALF-YEARLY EXAMINATION 2026–27
CLASS XII — MATHEMATICS (SET B)
MARKING SCHEME & STEP-BY-STEP SOLUTIONS
Time Allowed: 3 Hours                                          Maximum Marks: 80
--------------------------------------------------------------------------------
GENERAL GUIDELINES FOR EVALUATORS:
1. Marks should be awarded for every correct step as indicated in the marking scheme.
2. Full credit should be given for alternative correct methods.
3. For Section A MCQs, 1 mark is awarded for the correct option along with reasoning.
--------------------------------------------------------------------------------

SECTION A (20 Marks — 1 Mark Each)

Q1. (c) An equivalence relation                                              [1 mark]
    Reason: Reflexive: (1,1),(2,2),(3,3) in R. Symmetric: (1,3) & (3,1) in R. Transitive: all chains verified.

Q2. (b) 6                                                                    [1 mark]
    Reason: Number of one-one functions = 3! = 6.

Q3. (b) 3π/4                                                                 [1 mark]
    Reason: cos(3pi/4) = -1/sqrt(2) and 3pi/4 in [0, pi].

Q4. (b) (−π/2, π/2)                                                          [1 mark]
    Reason: Principal value branch of tan⁻¹x is (-pi/2, pi/2).

Q5. (a) Aᵀ + Bᵀ                                                              [1 mark]
    Reason: Transpose of sum property.

Q6. (a) 1                                                                    [1 mark]
    Reason: a_12 = -a_21 ==> a = -2; a_13 = -a_31 ==> -3 = -b ==> b = 3. a + b = 1.

Q7. (b) aᵢⱼ = 0 for i ≠ j                                                    [1 mark]
    Reason: Definition of diagonal matrix.

Q8. (b) −54                                                                  [1 mark]
    Reason: |3A| = 3^3 |A| = 27 * (-2) = -54.

Q9. (b) 15/2 sq units                                                        [1 mark]
    Reason: Area = (1/2)|1(0 - 3) + 6(3 - 0) + 4(0 - 0)| = (1/2)|-3 + 18| = 15/2.

Q10. (b) 3cos(3x)                                                            [1 mark]
    Reason: Chain rule: d/dx(sin 3x) = 3 cos 3x.

Q11. (c) Both continuous and differentiable everywhere on ℝ                  [1 mark]
    Reason: Polynomial functions are continuous and differentiable on all of R.

Q12. (a) 3/4                                                                 [1 mark]
    Reason: At x = 2: k(2^2) = 3 ==> 4k = 3 ==> k = 3/4.

Q13. (a) 4πr²                                                                [1 mark]
    Reason: dV/dr = (4/3)*pi*(3r^2) = 4*pi*r^2.

Q14. (b) (1, 3)                                                              [1 mark]
    Reason: f'(x) = 3(x - 1)(x - 3) < 0 for x in (1, 3).

Q15. (b) 24π cm²/cm                                                          [1 mark]
    Reason: dS/dr = 8*pi*r = 8*pi*3 = 24*pi.

Q16. (b) x = −1                                                              [1 mark]
    Reason: f'(x) = 6(x - 2)(x + 1) = 0. f''(-1) = 12(-1) - 6 = -18 < 0 ==> local max at x = -1.

Q17. (a) sin x + C                                                           [1 mark]
    Reason: Standard formula.

Q18. (a) sin⁻¹x + C                                                          [1 mark]
    Reason: Standard formula.

Q19. (a) Both A and R are true, and R is the correct explanation of A        [1 mark]
    Reason: LHL != RHL at every integer, so the limit does not exist, causing step discontinuities.

Q20. (a) Both A and R are true, and R is the correct explanation of A        [1 mark]
    Reason: f'(x) = 1/x > 0 for all x > 0 implies strictly increasing.

--------------------------------------------------------------------------------
SECTION B (5 × 2 = 10 Marks)

Q21. Check if R = {(1,1),(2,2),(3,3),(1,3),(3,1)} is an equivalence relation:
- Reflexive: (1,1),(2,2),(3,3) in R ==> Reflexive.                          [1/2 mark]
- Symmetric: (1,3) in R and (3,1) in R ==> Symmetric.                       [1/2 mark]
- Transitive: (1,3) & (3,1) -> (1,1) in R; (3,1) & (1,3) -> (3,3) in R ==> Transitive. [1/2 mark]
Conclusion: Since R is reflexive, symmetric and transitive, R is an equivalence relation. [1/2 mark]

Q22. Find A + 2B:
2B = [[2, 0], [-2, 4]]                                                       [1/2 mark]
A + 2B = [[3+2, 1+0], [-1-2, 2+4]] = [[5, 1], [-3, 6]]                      [1.5 marks]
Final Answer: [[5, 1], [-3, 6]]

Q23. Differentiate y = cos(x²):
dy/dx = -sin(x²) * d/dx(x²) = -2x sin(x²)                                    [2 marks]
Final Answer: -2x sin(x²)

Q24. Rate of increase of circle area:
A = pi * r^2
dA/dt = 2 * pi * r * (dr/dt)                                                 [1 mark]
Given dr/dt = 3 cm/s and r = 10 cm:
dA/dt = 2 * pi * 10 * 3 = 60*pi cm^2/s.                                      [1 mark]
Final Answer: 60π cm²/s

Q25. Evaluate ∫ e^(sin x) cos x dx:
Let u = sin x ==> du = cos x dx                                              [1 mark]
∫ e^(sin x) cos x dx = ∫ e^u du = e^u + C = e^(sin x) + C                    [1 mark]
Final Answer: e^(sin x) + C

--------------------------------------------------------------------------------
SECTION C (6 × 3 = 18 Marks)

Q26. Principal values:
(i) cos⁻¹(cos(5π/4)) = cos⁻¹(cos(2π - 3π/4)) = cos⁻¹(cos(3π/4)) = 3π/4      [1 mark]
(ii) sin⁻¹(sin(−π/3)) = −π/3 (since −π/3 in [−π/2, π/2])                    [1 mark]
(iii) tan⁻¹(tan(5π/6)) = tan⁻¹(tan(π - π/6)) = tan⁻¹(-tan(π/6)) = -π/6       [1 mark]

Q27. Domains & Principal value:
(i) sin⁻¹(3x - 1): -1 <= 3x - 1 <= 1 ==> 0 <= 3x <= 2 ==> Domain: [0, 2/3]   [1 mark]
(ii) cos⁻¹((x - 1)/2): -1 <= (x - 1)/2 <= 1 ==> -2 <= x - 1 <= 2 ==> Domain: [-1, 3] [1 mark]
(iii) tan⁻¹(-1) = -π/4                                                       [1 mark]

Q28. Determinant evaluation:
|A| = 2(2*2 - (-1)*3) - (-1)(1*2 - 0) + 3(1*3 - 0)                           [1 mark]
    = 2(4 + 3) + 1(2) + 3(3)                                                 [1 mark]
    = 2(7) + 2 + 9 = 14 + 2 + 9 = 25                                         [1 mark]
Final Answer: 25

Q29. y = x^(sin x) (x > 0):
ln y = sin x * ln x                                                          [1/2 mark]
(1/y) dy/dx = cos x * ln x + (sin x)/x                                       [1.5 marks]
dy/dx = x^(sin x) [cos x * ln x + (sin x)/x]                                 [1 mark]
Final Answer: x^(sin x) [cos x * ln x + (sin x)/x]

Q30. Intervals of increase/decrease for f(x) = x³ − 6x² + 9x + 15:
f'(x) = 3x² − 12x + 9 = 3(x² − 4x + 3) = 3(x − 1)(x − 3)                     [1 mark]
Critical points: x = 1, x = 3
- In (-inf, 1): f'(x) > 0 ==> Strictly Increasing                            [1 mark]
- In (1, 3): f'(x) < 0 ==> Strictly Decreasing                               [1/2 mark]
- In (3, inf): f'(x) > 0 ==> Strictly Increasing                             [1/2 mark]
Final Answer: (i) Strictly increasing on (-∞, 1) ∪ (3, ∞); (ii) Strictly decreasing on (1, 3)

Q31. Evaluate ∫ x cos x dx:
Using integration by parts: u = x ==> u' = 1; v' = cos x ==> v = sin x       [1 mark]
∫ x cos x dx = x sin x - ∫ 1 * sin x dx                                      [1 mark]
             = x sin x - (-cos x) + C = x sin x + cos x + C                  [1 mark]
Final Answer: x sin x + cos x + C

--------------------------------------------------------------------------------
SECTION D (4 × 5 = 20 Marks)

Q32. Express A = [[3, -2, 5], [4, 1, -3], [-1, 2, 1]] as P + Q:
A^T = [[3, 4, -1], [-2, 1, 2], [5, -3, 1]]                                   [1 mark]
P = (A + A^T)/2 = [[3, 1, 2], [1, 1, -1/2], [2, -1/2, 1]]                    [1.5 marks]
Q = (A - A^T)/2 = [[0, -3, 3], [3, 0, -5/2], [-3, 5/2, 0]]                  [1.5 marks]
Verification: P^T = P, Q^T = -Q, and P + Q = A.                              [1 mark]

Q33. Solve system using matrix method:
A = [[1,-1,2],[3,4,-5],[2,-1,3]], X = [[x],[y],[z]], B = [[7],[-5],[12]]
|A| = 1(12 - 5) - (-1)(9 - (-10)) + 2(-3 - 8) = 7 + 19 - 22 = 4 != 0        [1 mark]
Cofactors:
C_11 = 7, C_12 = -19, C_13 = -11
C_21 = 1, C_22 = -1, C_23 = -1
C_31 = -3, C_32 = 11, C_33 = 7                                               [1.5 marks]
adj A = [[7, 1, -3], [-19, -1, 11], [-11, -1, 7]]                            [1/2 mark]
A^(-1) = (1/4) adj A                                                         [1/2 mark]
X = A^(-1) B:
x = (1/4)(7*7 + 1*(-5) + (-3)*12) = (1/4)(49 - 5 - 36) = 8/4 = 2
y = (1/4)(-19*7 + (-1)*(-5) + 11*12) = (1/4)(-133 + 5 + 132) = 4/4 = 1
z = (1/4)(-11*7 + (-1)*(-5) + 7*12) = (1/4)(-77 + 5 + 84) = 12/4 = 3       [1.5 marks]
Final Answer: x = 2, y = 1, z = 3

Q34. y = (tan⁻¹x)², show (1 + x²)² y₂ + 2x(1 + x²) y₁ = 2:
y₁ = 2 tan⁻¹x * (1/(1 + x²))                                                 [1 mark]
(1 + x²) y₁ = 2 tan⁻¹x                                                       [1 mark]
Differentiating with respect to x:
(1 + x²) y₂ + 2x y₁ = 2 / (1 + x²)                                           [1.5 marks]
Multiplying throughout by (1 + x²):
(1 + x²)² y₂ + 2x(1 + x²) y₁ = 2. Hence Proved.                              [1.5 marks]

Q35. Evaluate ∫ (x² + x + 1)/((x + 1)(x + 2)) dx:
(x + 1)(x + 2) = x² + 3x + 2
(x² + x + 1)/(x² + 3x + 2) = 1 + (-2x - 1)/(x² + 3x + 2)                     [1 mark]
Partial fractions: (-2x - 1)/((x + 1)(x + 2)) = A/(x + 1) + B/(x + 2)        [1/2 mark]
-2x - 1 = A(x + 2) + B(x + 1)
At x = -1: 1 = A(1) ==> A = 1
At x = -2: 3 = B(-1) ==> B = -3                                              [1.5 marks]
∫ [1 + 1/(x + 1) - 3/(x + 2)] dx = x + ln|x + 1| - 3 ln|x + 2| + C           [2 marks]
Final Answer: x + ln|x + 1| - 3 ln|x + 2| + C

--------------------------------------------------------------------------------
SECTION E (3 × 4 = 12 Marks)

Q36. Case Study 1 — Relations and Functions:
(i) Reflexive & Transitive:
- Reflexive: Every integer a divides itself ==> (a, a) in R for all a in A.   [1/2 mark]
- Transitive: If a divides b and b divides c, then b = k_1 a and c = k_2 b ==> c = (k_1 k_2) a ==> a divides c. So (a, b) in R and (b, c) in R ==> (a, c) in R. [1.5 marks]

(ii) Is R symmetric?
No. For example, (1, 2) in R because 1 divides 2, but (2, 1) not in R because 2 does not divide 1. [1 mark]

(iii) Is R an equivalence relation?
No, because R is not symmetric.                                              [1 mark]

Q37. Case Study 2 — Application of Derivatives:
(i) MR(x) = dR/dx = 26x + 26                                                 [2 marks]

(ii) Marginal revenue when x = 7:
MR(7) = 26(7) + 26 = 182 + 26 = ₹ 208                                        [1 mark]

(iii) Revenue increasing/decreasing:
Since MR(7) = 208 > 0, the total revenue is increasing at x = 7 units.       [1 mark]

Q38. Case Study 3 — Integrals:
(i) Displacement s(t):
s(t) = ∫ (6t² − 4t) dt = 2t³ − 2t² + C                                       [1 mark]
Given s(0) = 0 ==> C = 0. Thus, s(t) = 2t³ − 2t².                           [1 mark]

(ii) Displacement at t = 3 s:
s(3) = 2(3³) − 2(3²) = 2(27) − 2(9) = 54 − 18 = 36 m.                       [1 mark]

(iii) Momentarily at rest (t > 0):
v(t) = 0 ==> 6t² − 4t = 0 ==> 2t(3t − 2) = 0 ==> t = 2/3 s.                 [1 mark]

— End of Marking Scheme —
""")

    print("Set B text files written.")


if __name__ == '__main__':
    write_text_files()
    write_set_b_text_files()
