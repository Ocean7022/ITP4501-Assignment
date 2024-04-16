package com.example.itp4501_assignment;

public class CardPhotoSet {
    private String style;
    int[] photoSet = new int[4];
    private int[] photoSource;
    private int[] bgImgSource;

    public CardPhotoSet(String style) {
        this.style = style;

        if (style.equals("Number")) {
            setNumberSet();
            setBgImageNumberSet();
        } else if (style.equals("艦これ")) {
            setKankoreSet();
            setBgImageKankoreSet();
        } else if (style.equals("ウマ娘")) {
            setUmaSet();
            setBgImageUmaSet();
        }

        randomPhoto(photoSource);
    }

    public void randomPhoto(int[] photos) {
        int randomPhoto = 0;
        for (int i = 0; i < photoSet.length;) {
            randomPhoto = (int) (Math.random() * photos.length);

            boolean isDuplicate = false;
            for (int x = 0; x < i; x++) {
                if (photoSet[x] == photos[randomPhoto]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate)
                continue;

            photoSet[i] = photos[randomPhoto];
            i++;
        }
    }

    public int[] getPhotoSet() {
        return photoSet;
    }

    public int getBgImg() {
        return bgImgSource[(int) (Math.random() * bgImgSource.length)];
    }

    public void setNumberSet() {
        this.photoSource = new int[] {
                R.drawable.card_number01,
                R.drawable.card_number02,
                R.drawable.card_number03,
                R.drawable.card_number04
        };
    }

    public void setBgImageNumberSet() {
        this.bgImgSource = new int[] {
                R.drawable.bg_number01,
                R.drawable.bg_number02,
                R.drawable.bg_number03,
                R.drawable.bg_number04,
                R.drawable.bg_number05,
                R.drawable.bg_number06,
                R.drawable.bg_number07,
                R.drawable.bg_number08
        };
    }

    public void setKankoreSet() {
        this.photoSource = new int[] {
                R.drawable.card_kankore01,
                R.drawable.card_kankore02,
                R.drawable.card_kankore03,
                R.drawable.card_kankore04,
                R.drawable.card_kankore05,
                R.drawable.card_kankore06,
                R.drawable.card_kankore07,
                R.drawable.card_kankore08,
                R.drawable.card_kankore09,
                R.drawable.card_kankore10,
                R.drawable.card_kankore11,
                R.drawable.card_kankore12,
                R.drawable.card_kankore13,
                R.drawable.card_kankore14,
                R.drawable.card_kankore15,
                R.drawable.card_kankore16,
                R.drawable.card_kankore17,
                R.drawable.card_kankore18,
                R.drawable.card_kankore19,
                R.drawable.card_kankore20,
        };
    }

    public void setBgImageKankoreSet() {
        this.bgImgSource = new int[] {
                R.drawable.bg_kankore01
        };
    }

    public void setUmaSet() {
        this.photoSource = new int[] {
                R.drawable.card_uma01,
                R.drawable.card_uma02,
                R.drawable.card_uma03,
                R.drawable.card_uma04,
                R.drawable.card_uma05,
                R.drawable.card_uma06,
                R.drawable.card_uma07,
                R.drawable.card_uma08,
                R.drawable.card_uma09,
                R.drawable.card_uma10,
                R.drawable.card_uma11,
                R.drawable.card_uma12,
                R.drawable.card_uma13,
                R.drawable.card_uma14,
                R.drawable.card_uma15,
                R.drawable.card_uma16,
                R.drawable.card_uma17,
                R.drawable.card_uma18,
                R.drawable.card_uma19,
                R.drawable.card_uma20,
                R.drawable.card_uma21,
                R.drawable.card_uma22,
                R.drawable.card_uma23,
                R.drawable.card_uma24,
                R.drawable.card_uma25,
                R.drawable.card_uma26,
                R.drawable.card_uma27
        };
    }

    public void setBgImageUmaSet() {
        this.bgImgSource = new int[] {
                R.drawable.bg_uma01,
                R.drawable.bg_uma02,
                R.drawable.bg_uma03
        };
    }
}
