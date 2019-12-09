using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class main : MonoBehaviour
{
    public Button button;
    public GameObject panel;
    public move player;
    public score score;
    void Start()
    {
        Button btn = button.GetComponent<Button>();
        btn.onClick.AddListener(OnClick);
    }

    void OnClick()
    {
        panel.gameObject.SetActive(false);
        player.enabled = true;
        score.enabled = true;
    }
}
