using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class skill : MonoBehaviour
{
    Image skillBar;
    float timer;
    public float CD;
    public GameObject player;
    void Start()
    {
        skillBar = transform.Find("skillBar").GetComponent<Image>();
        skillBar.fillMethod = Image.FillMethod.Radial360;
        skillBar.fillOrigin = 0;
        PlayerPrefs.SetFloat("position", 0);
    }

    // Update is called once per frame
    void Update()
    {
        timer += Time.deltaTime;
        skillBar.fillAmount = timer / CD;
    }

    public void OnClick()
    {
        if (skillBar.fillAmount >= 1)
        {
            timer = 0;
            PlayerPrefs.SetFloat("position", player.transform.position.z);
        }
    }
}
