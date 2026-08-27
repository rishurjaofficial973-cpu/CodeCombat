"""
Native Word OMML Matrix & Determinant Builder
"""

from docx.oxml import parse_xml

def make_omml_matrix(rows, bracket='['):
    """
    rows: 2D list of strings, e.g. [['1', '2'], ['3', '4']]
    bracket: '[' for square bracket matrix, '|' for determinant, '(' for paren
    """
    beg_chr = '|' if bracket == '|' else ('(' if bracket == '(' else '[')
    end_chr = '|' if bracket == '|' else (')' if bracket == '(' else ']')
    
    mr_xml_list = []
    for row in rows:
        e_xml_list = []
        for cell in row:
            e_xml_list.append(f'<m:e><m:r><m:rPr><m:scr m:val="roman"/><m:sty m:val="p"/></m:rPr><m:t>{cell}</m:t></m:r></m:e>')
        mr_xml_list.append(f'<m:mr>{"".join(e_xml_list)}</m:mr>')
        
    m_body = "".join(mr_xml_list)
    
    xml = f"""
    <m:oMath xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math">
      <m:d>
        <m:dPr>
          <m:begChr m:val="{beg_chr}"/>
          <m:endChr m:val="{end_chr}"/>
          <m:grow m:val="on"/>
        </m:dPr>
        <m:e>
          <m:m>
            <m:mPr>
              <m:baseJc m:val="center"/>
              <m:plcHide m:val="on"/>
            </m:mPr>
            {m_body}
          </m:m>
        </m:e>
      </m:d>
    </m:oMath>
    """
    return parse_xml(xml)


def append_omml(paragraph, omml_element):
    """Appends an OMML math element to a python-docx paragraph."""
    paragraph._element.append(omml_element)
