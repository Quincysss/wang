using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class colorShow : MonoBehaviour
{
    public Image image1;
    public Image image2;
    public Image image3;
    Image[] image_list = new Image[3];
    Color[] color_list = new Color[3];
    void Start()
    {
        image1 = transform.Find("1").GetComponent<Image>();
        image2 = transform.Find("2").GetComponent<Image>();
        image3 = transform.Find("3").GetComponent<Image>();
        image_list[0] = image1;
        image_list[1] = image2;
        image_list[2] = image3;
        color_list[0] = Color.blue;
        color_list[1] = Color.yellow;
        color_list[2] = Color.green;
    }

    public void setColor(int i, int color)
    {
        image_list[i].color = color_list[color];
        if (i == 2)
        {
            StartCoroutine(colordis());
        }
    }

    IEnumerator colordis()
    {
        yield return new WaitForSeconds((float)0.1);
        foreach (Image image in image_list)
        {
            image.color = Color.white;
        }
    }
}
